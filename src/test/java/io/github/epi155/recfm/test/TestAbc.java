package io.github.epi155.recfm.test;

import io.github.epi155.recfm.exec.RecordFormatMojo;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestAbc  extends AbstractMojoTestCase {

    @Test
    public void testBasic() throws IllegalAccessException, MojoExecutionException, XmlPullParserException, IOException {
        RecordFormatMojo mojo = new RecordFormatMojo();
        setVariableValueToObject(mojo, "generateDirectory", new File("target/generated-test-sources/recfm"));
        setVariableValueToObject(mojo, "settingsDirectory", new File("src/test/resources"));
        setVariableValueToObject(mojo, "plugin",  new PluginDescriptor() {
            public String getGroupId() {
                return "io.github.epi155";
            }
            public String getArtifactId() {
                return "recfm-maven-plugin";
            }
            public String getVersion() {
                return "TEST";
            }
        });
        setVariableValueToObject(mojo, "settings", new String[]{"Abc-01.yaml"});
        setVariableValueToObject(mojo, "addCompileSourceRoot", false);
        setVariableValueToObject(mojo, "addTestCompileSourceRoot", true);


        File pomFile = new File("pom.xml");
        MavenProject project = getProject(pomFile.toPath());
        setVariableValueToObject(mojo, "project", project);


        Assertions.assertDoesNotThrow(mojo::execute);
    }

    MavenProject getProject(Path pomPath) throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try (InputStream is = Files.newInputStream(pomPath)) {
            Model model = reader.read(is);
            return new MavenProject(model);
        }
    }

}
