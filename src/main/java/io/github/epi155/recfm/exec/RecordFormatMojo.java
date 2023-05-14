package io.github.epi155.recfm.exec;


import io.github.epi155.recfm.api.CodeProvider;
import io.github.epi155.recfm.type.*;
import io.github.epi155.recfm.util.GenerateArgs;
import io.github.epi155.recfm.util.Tools;
import lombok.Getter;
import lombok.val;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.ServiceLoader;

@Mojo(name = "generate",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        requiresDependencyCollection = ResolutionScope.COMPILE
)
public class RecordFormatMojo extends AbstractMojo {

    private static final String SET_LENGTH = "setLength";
    private static final String GET_LENGTH = "getLength";

    private static final String SET_OFFSET = "setOffset";
    private static final String GET_OFFSET = "getOffset";

    private static final String SET_REDEFINES = "setRedefines";
    private static final String GET_REDEFINES = "getRedefines";

    private static final String GET_CHECK = "getCheck";
    private static final String SET_CHECK = "setCheck";

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

    @Parameter(defaultValue = "4", property = "maven.recfm.align", required = true)
    private int align;

    @Parameter(defaultValue = "false", property = "maven.recfm.doc", required = true)
    private boolean doc;
    @Parameter(defaultValue = "true", property = "maven.recfm.enforceGetter", required = true)
    private boolean enforceGetter;
    @Parameter(defaultValue = "true", property = "maven.recfm.enforceSetter", required = true)
    private boolean enforceSetter;

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

    public static Yaml prepareYaml() {
        Constructor constructor = new Constructor(ClassesDefine.class, new LoaderOptions());
        Representer representer = new Representer(new DumperOptions());

        tuningClassDef(constructor, representer);
        tuningField(constructor, representer, "!Abc", FieldAbc.class);
        tuningField(constructor, representer, "!Num", FieldNum.class);
        tuningField(constructor, representer, "!Cus", FieldCustom.class);
        tuningField(constructor, representer, "!Dom", FieldDomain.class);
        tuningField(constructor, representer, "!Fil", FieldFiller.class);
        tuningField(constructor, representer, "!Val", FieldConstant.class);
        tuningField(constructor, representer, "!Grp", FieldGroup.class);
        tuningField(constructor, representer, "!Occ", FieldOccurs.class);
        tuningField(constructor, representer, "!GRP", FieldGroupProxy.class);
        tuningField(constructor, representer, "!OCC", FieldOccursProxy.class);

        return new Yaml(constructor, representer);
    }

    private static void tuningField(Constructor c, Representer r, String tag, Class<? extends NakedField> f) {
        TypeDescription td = new TypeDescription(f, tag);
        td.substituteProperty("at", int.class, GET_OFFSET, SET_OFFSET);
        td.substituteProperty("len", int.class, GET_LENGTH, SET_LENGTH);
        if (NamedField.class.isAssignableFrom(f)) {
            td.substituteProperty("red", boolean.class, GET_REDEFINES, SET_REDEFINES);
        }
        if (FloatingField.class.isAssignableFrom(f)) {
            td.substituteProperty("ovf", OverflowAction.class, "getOnOverflow", "setOnOverflow");
            td.substituteProperty("unf", UnderflowAction.class, "getOnUnderflow", "setOnUnderflow");
        }
        if (f == FieldOccurs.class)
            td.substituteProperty("x", int.class, "getTimes", "setTimes");
        else if (f == FieldAbc.class) {
            td.substituteProperty("pad", Character.class, "getPadChar", "setPadChar");
            td.substituteProperty("chk", CheckChar.class, GET_CHECK, SET_CHECK);
        } else if (f == FieldNum.class)
            td.substituteProperty("num", boolean.class, "getNumericAccess", "setNumericAccess");
        else if (f == FieldConstant.class)
            td.substituteProperty("val", String.class, "getValue", "setValue");
        else if (f == FieldFiller.class) {
            td.substituteProperty("chr", String.class, "getFillChar", "setFillChar");
            td.substituteProperty("chk", CheckChar.class, GET_CHECK, SET_CHECK);
        } else if (f == FieldCustom.class) {
            td.substituteProperty("ini", Character.class, "getInitChar", "setInitChar");
            td.substituteProperty("pad", Character.class, "getPadChar", "setPadChar");
            td.substituteProperty("chk", CheckUser.class, GET_CHECK, SET_CHECK);
        }
        c.addTypeDescription(td);
        r.addTypeDescription(td);
    }

    private static void tuningClassDef(Constructor constructor, Representer representer) {
        TypeDescription td = new TypeDescription(ClassDefine.class);
        td.substituteProperty("len", int.class, GET_LENGTH, SET_LENGTH);
        constructor.addTypeDescription(td);
        representer.addTypeDescription(td);
    }

    public void execute() throws MojoExecutionException {
        getLog().info("Check for output directory ...");

        Yaml yaml = prepareYaml();

        val args = GenerateArgs.builder()
            .sourceDirectory(generateDirectory)
            .align(align)
            .doc(doc)
            .setCheck(enforceSetter)
            .getCheck(enforceGetter)
            .group(plugin.getGroupId())
            .artifact(plugin.getArtifactId())
            .version(plugin.getVersion())
            .build();

        val driver = getCodeProvider();
        getLog().info("Settings directory: " + settingsDirectory);
        for (String setting : settings) {
            getLog().info("Generate from " + setting);
            try (InputStream inputStream = new FileInputStream(settingsDirectory + File.separator + setting)) {
                ClassesDefine structs = yaml.load(inputStream);

                String cwd = Tools.makeDirectory(args.sourceDirectory, structs.getPackageName());
                structs.getProxies()
                        .forEach(it -> generateProxy(it, driver, structs, args));
                structs.getClasses().
                    forEach(it -> generateClass(it, driver, structs, args));
            } catch (FileNotFoundException e) {
                getLog().warn("Setting " + setting + " does not exist, ignored.");
            } catch (Exception e) {
                getLog().error(e.toString());
                throw new MojoExecutionException("Failed to execute plugin", e);
            }
        }
        setupMavenPaths();

        getLog().info("Done.");
    }

    private void generateProxy(ClassDefine proxy, CodeProvider driver, ClassesDefine structs, GenerateArgs ga) {
        if (proxy.getFields().isEmpty()) return;
        val base = proxy.getFields().get(0).getOffset();

        val wrtPackage = structs.getPackageName();
        getLog().info("- Prepare interface "+proxy.getName()+" ...");

        proxy.checkForVoid();
        boolean checkSuccesful = proxy.noBadName();
        checkSuccesful &= proxy.noDuplicateName(Tools::testCollision);
        checkSuccesful &= proxy.noHole(base);
        checkSuccesful &= proxy.noOverlap(base);
        if (checkSuccesful) {
            getLog().info("  [####o] Creating ...");
            driver.createInterface(generateDirectory, wrtPackage, proxy, ga);
            getLog().info("  [#####] Created.");
        } else {
            throw new ClassDefineException("Class <" + proxy.getName() + "> bad defined");
        }
    }

    private void generateClass(ClassDefine clazz, CodeProvider driver, ClassesDefine structs, GenerateArgs ga) {
        val wrtPackage = structs.getPackageName();
        val defaults = structs.getDefaults();

        getLog().info("- Prepare class "+clazz.getName()+" ...");

        clazz.checkForVoid();

        boolean checkSuccesful = clazz.noBadName();
        checkSuccesful &= clazz.noDuplicateName(Tools::testCollision);
        checkSuccesful &= clazz.noHole();
        checkSuccesful &= clazz.noOverlap();
        if (checkSuccesful) {
            getLog().info("  [####o] Creating ...");
            driver.createClass(generateDirectory, wrtPackage, clazz, ga, defaults);
            getLog().info("  [#####] Created.");
        } else {
            throw new ClassDefineException("Class <" + clazz.getName() + "> bad defined");
        }
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
