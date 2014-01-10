package org.wetator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;
import org.wetator.core.WetatorEngine;
import org.wetator.exception.InvalidInputException;

import java.io.File;

@Mojo(name = "execute", defaultPhase = LifecyclePhase.INTEGRATION_TEST)
public class WetatorMojo extends AbstractMojo {

    @Parameter(property = "execute.configFile")
    private String configFile;

    @Parameter(property = "execute.testFileDir")
    private String testFileDir;

    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            final WetatorEngine tmpWetatorEngine = new WetatorEngine();
            tmpWetatorEngine.setConfigFileName(configFile);

            tmpWetatorEngine.init();

            // add all files
            final DirectoryScanner directoryScanner = new DirectoryScanner();
            directoryScanner.setBasedir(testFileDir);
            directoryScanner.setIncludes(new String[]{"**\\*.wet"});
            directoryScanner.setCaseSensitive(true);
            directoryScanner.scan();

            final String[] tmpListOfFiles = directoryScanner.getIncludedFiles();

            for (int i = 0; i < tmpListOfFiles.length; i++) {
                final String tmpFileName = tmpListOfFiles[i];
                tmpWetatorEngine.addTestCase(tmpFileName, new File(directoryScanner.getBasedir(), tmpFileName));
            }

            tmpWetatorEngine.executeTests();
        } catch (InvalidInputException e) {
            getLog().error(e.getMessage(), e);
            throw new MojoExecutionException(e.getMessage(), e);
        } catch (Exception e) {
            getLog().error(e.getMessage(), e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
