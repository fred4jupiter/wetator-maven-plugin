package org.wetator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.DirectoryScanner;
import org.wetator.core.WetatorEngine;

import java.io.File;

/**
 * This is the main Mojo for executing wetator tests.
 */
@Mojo(name = "execute")
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
        getLog().info("start running wetator tests ...");
        getLog().info("using config file: " + configFile);
        getLog().info("using wetator test file directory: " + testFileDir);

        try {
            final WetatorEngine wetatorEngine = new WetatorEngine();
            wetatorEngine.setConfigFileName(configFile);

            wetatorEngine.init();

            final String[] weatorTestFilenames = scanForWetatorTestFiles();
            for (String weatorTestFilename : weatorTestFilenames) {
                getLog().info("adding test file: " + weatorTestFilename);
                wetatorEngine.addTestCase(weatorTestFilename, new File(testFileDir, weatorTestFilename));
            }

            wetatorEngine.executeTests();

            getLog().info("find wetator test results in: " + wetatorEngine.getConfiguration().getOutputDir().getCanonicalPath());
            getLog().info("wetator test execution complete!");
        } catch (Exception e) {
            getLog().error(e.getMessage(), e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private String[] scanForWetatorTestFiles() {
        final DirectoryScanner directoryScanner = new DirectoryScanner();
        directoryScanner.setBasedir(testFileDir);
        directoryScanner.setIncludes(new String[]{INCLUDE_FILE_PATTERN});
        directoryScanner.scan();

        return directoryScanner.getIncludedFiles();
    }
}
