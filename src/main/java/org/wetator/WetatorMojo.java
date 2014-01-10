package org.wetator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;
import org.wetator.core.WetatorEngine;

import java.io.File;

/**
 * This is the main Mojo for executing wetator tests.
 */
@Mojo(name = "execute", defaultPhase = LifecyclePhase.INTEGRATION_TEST)
public class WetatorMojo extends AbstractMojo {

    /**
     * File pattern for the weator test files.
     */
    private static final String INCLUDE_FILE_PATTERN = "**\\*.wet";

    /**
     * Path with filename to the config file in file system.
     */
    @Parameter(property = "execute.configFile")
    private String configFile;

    /**
     * Directory where the test files resides.
     */
    @Parameter(property = "execute.testFileDir")
    private String testFileDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            final WetatorEngine wetatorEngine = new WetatorEngine();
            wetatorEngine.setConfigFileName(configFile);

            wetatorEngine.init();

            // add all files
            final DirectoryScanner directoryScanner = new DirectoryScanner();
            directoryScanner.setBasedir(testFileDir);
            directoryScanner.setIncludes(new String[]{INCLUDE_FILE_PATTERN});
            directoryScanner.setCaseSensitive(true);
            directoryScanner.scan();

            final String[] weatorTestFilename = directoryScanner.getIncludedFiles();

            for (int i = 0; i < weatorTestFilename.length; i++) {
                final String filename = weatorTestFilename[i];
                wetatorEngine.addTestCase(filename, new File(directoryScanner.getBasedir(), filename));
            }

            wetatorEngine.executeTests();
        } catch (Exception e) {
            getLog().error(e.getMessage(), e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
