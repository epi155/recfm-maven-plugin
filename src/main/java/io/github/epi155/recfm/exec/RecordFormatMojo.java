package io.github.epi155.recfm.exec;


import io.github.epi155.recfm.api.*;
import lombok.Getter;
import lombok.val;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mojo(name = "generate",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        requiresDependencyCollection = ResolutionScope.COMPILE
)
public class RecordFormatMojo extends AbstractMojo {
    private static final Pattern pattern = Pattern.compile("^\\s*#!import\\s+(\\S+)\\s*$");
//    private static final Pattern pattern = Pattern.compile("\\s*#!import\\s+\"([^\"]+)\"\\s*");
    /**
     * <p>
     * Generated code will be written under this directory.
     * </p>
     * <p>
     * For instance, if you specify <code>generateDirectory="doe/ray"</code> and
     * <code>generatePackage="org.here"</code>, then files are generated to
     * <code>doe/ray/org/here</code>.
     * </p>
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/recfm",
        property = "maven.recfm.generateDirectory", required = true)
    @Getter
    private File generateDirectory;

    @Parameter(defaultValue = "${project.build.resources[0].directory}",
        property = "maven.recfm.settingsDirectory", required = true)
    private String settingsDirectory;
    @Parameter(defaultValue = "false", property = "maven.recfm.preprocessor", required = true)
    private boolean preprocessor;

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    @Parameter(required = true)
    private String[] settings;

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;
    @Parameter(defaultValue="${plugin}", readonly=true, required=true)
    protected org.apache.maven.plugin.descriptor.PluginDescriptor plugin;

    /**
     * If set to true (default), adds target directory as a compile source root
     * of this Maven project.
     */
    @Parameter(defaultValue = "true", property = "maven.recfm.addCompileSourceRoot")
    private boolean addCompileSourceRoot = true;

    /**
     * If set to true, adds target directory as a test compile source root of
     * this Maven project. Default value is false.
     */
    @Parameter(defaultValue = "false", property = "maven.recfm.addTestCompileSourceRoot")
    private boolean addTestCompileSourceRoot = false;

    @Parameter
    private String codeProviderClassName;

    private final Set<String> classLogbook = new HashSet<>();

    public void execute() throws MojoExecutionException {
        val driver = getCodeProvider();
        val factory = driver.getInstance();

        Constructor c0 = new Constructor(MasterBook.class, new LoaderOptions());
        c0.addTypeDescription(new ClassDescription(factory));
        c0.addTypeDescription(new TraitDescription(factory));
        c0.addTypeDescription(new AbcDescription(factory));
        c0.addTypeDescription(new NumDescription(factory));
        c0.addTypeDescription(new NuxDescription(factory));
        c0.addTypeDescription(new CusDescription(factory));
        c0.addTypeDescription(new DomDescription(factory));
        c0.addTypeDescription(new FilDescription(factory));
        c0.addTypeDescription(new ValDescription(factory));
        c0.addTypeDescription(new EmbDescription(factory));
        c0.addTypeDescription(new GrpDescription(factory));
        c0.addTypeDescription(new OccDescription(factory));
        c0.addTypeDescription(new GrpTraitDescription(factory));
        c0.addTypeDescription(new OccTraitDescription(factory));

        c0.addTypeDescription(new ClsDfltDescription());
        c0.addTypeDescription(new AbcDfltDescription());
        c0.addTypeDescription(new NumDfltDescription());
        c0.addTypeDescription(new CusDfltDescription());
        c0.addTypeDescription(new NuxDfltDescription());
        c0.addTypeDescription(new FilDfltDescription());

        c0.addTypeDescription(new InitNuxModeDescription());
        c0.addTypeDescription(new AccesModeDescription());
        c0.addTypeDescription(new WordWidthDescription());

        Yaml yaml = new Yaml(c0);

        val args = GenerateArgs.builder()
            .sourceDirectory(generateDirectory)
            .group(plugin.getGroupId())
            .artifact(plugin.getArtifactId())
            .version(plugin.getVersion())
            .build();

        getLog().info("Settings directory: " + settingsDirectory);
        for (String setting : settings) {
            getLog().info("Generate from " + setting);
            File configFile = new File(settingsDirectory + File.separator + setting);
            if (!configFile.exists()) {
                getLog().warn("Setting " + setting + " does not exist, ignored.");
                continue;
            }
            try {
                if (preprocessor) {
                    configFile = preprocess(configFile);
                }
                try (InputStream inputStream = Files.newInputStream(configFile.toPath())) {
                    MasterBook book = yaml.load(inputStream);

                    FieldDefault defaults = book.getDefaults();
                    for( val packg: book.getPackages()) {
                        String namespace = packg.getName();
                        makeDirectory(args.sourceDirectory, namespace);
                        for(val it: packg.getInterfaces()) {
                            generateTrait(it, namespace, args, defaults);
                        }
                        for(val it: packg.getClasses()) {
                            generateClass(it, namespace, args, defaults);
                        }
                    }
                }
            } catch (MojoExecutionException e) {
                throw e;
            } catch (Exception e) {
                getLog().error(e.toString());
                throw new MojoExecutionException("Failed to execute plugin", e);
            }
        }
        setupMavenPaths();

        getLog().info("Done.");
    }

    public void makeDirectory(@NotNull File base, @Nullable String packg) throws MojoExecutionException {
        if (!base.exists()) {
            getLog().info("Base Directory <"+base.getName()+"> does not exist, creating");
            if (!base.mkdirs())
                throw new MojoExecutionException("Error creating Base Directory <" + base.getName() + ">");
        }
        if (!base.isDirectory()) throw new MojoExecutionException("Base Directory <" + base.getName() + "> is not a Directory");
        if (packg == null) return;
        StringTokenizer st = new StringTokenizer(packg, ".");
        String cwd = base.getAbsolutePath();
        while (st.hasMoreElements()) {
            val d = st.nextElement();
            val tmp = cwd + File.separator + d;
            mkdir(tmp);
            cwd = tmp;
        }
    }

    private static void mkdir(String tmp) throws MojoExecutionException {
        val f = new File(tmp);
        if ((!f.exists()) && (!f.mkdir()))
            throw new MojoExecutionException("Cannot create directory <" + tmp + ">");
    }

    private File preprocess(File configFile) throws IOException {
        File tempConfig = File.createTempFile("tecfm-", ".yaml");
        tempConfig.deleteOnExit();
        try (BufferedWriter bw = Files.newBufferedWriter(tempConfig.toPath())) {
            append(bw, configFile);
        }
        return tempConfig;
    }

    private void append(BufferedWriter bw, File configFile) throws IOException {
        try(BufferedReader br = Files.newBufferedReader(configFile.toPath())) {
            String line;
            while((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if(matcher.matches()) {
                    String inside = matcher.group(1);
                    File insideConf = inside.startsWith(File.separator) ?
                            new File(inside) : new File(settingsDirectory + File.separator + inside);
                    if (insideConf.exists()) {
                        getLog().info("<< import "+inside);
                        append(bw, insideConf);
                    } else {
                        getLog().warn("Skip import "+insideConf.getAbsolutePath());
                    }
                } else {
                    bw.write(line);
                    bw.newLine();
                }
            }
        }
    }

    private void generateTrait(TraitModel trait, String namespace, GenerateArgs ga, FieldDefault defaults) throws MojoExecutionException {
        val classFullName = namespace+"."+trait.getName();
        if (! classLogbook.add(classFullName)) {
            throw new MojoExecutionException("Class <" + classFullName + "> duplicated");
        }

        trait.create(namespace, ga, defaults);
    }

    private void generateClass(ClassModel clazz, String namespace, GenerateArgs ga, FieldDefault defaults) throws MojoExecutionException {
        val classFullName = namespace+"."+clazz.getName();
        if (! classLogbook.add(classFullName)) {
            throw new MojoExecutionException("Class <" + classFullName + "> duplicated");
        }

        clazz.create(namespace, ga, defaults);
    }

    private CodeProvider getCodeProvider() {
        ServiceLoader<CodeProvider> loader = ServiceLoader.load(CodeProvider.class);
        for (CodeProvider codeProvider : loader) {
            val codeProviderItem = codeProvider.getClass().getName();
            if (codeProviderClassName ==null || codeProviderClassName.equals(codeProviderItem)) {
                getLog().info("Using Code Provider: "+codeProviderItem);
                return codeProvider;
            }
            getLog().info("Skip Code Provider: "+codeProviderItem);
        }
        if (codeProviderClassName == null) {
            throw new CodeDriverException();
        } else {
            throw new CodeDriverException(codeProviderClassName);
        }
    }
    private void setupMavenPaths() {
        if (getAddCompileSourceRoot()) {
            getProject().addCompileSourceRoot(getGenerateDirectory().getPath());
        }
        if (getAddTestCompileSourceRoot()) {
            getProject().addTestCompileSourceRoot(getGenerateDirectory().getPath());
        }
    }

    private MavenProject getProject() {
        return project;
    }

    private boolean getAddTestCompileSourceRoot() {
        return addTestCompileSourceRoot;
    }

    private boolean getAddCompileSourceRoot() {
        return addCompileSourceRoot;
    }

}
