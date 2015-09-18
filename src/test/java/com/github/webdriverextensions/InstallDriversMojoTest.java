package com.github.webdriverextensions;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class InstallDriversMojoTest extends AbstractInstallDriverMojoTest {

    public void test_random_configuration() throws Exception {
        InstallDriversMojo mojo = getMojo("src/test/resources/test-mojo-configuration-pom.xml", "install-drivers");

        mojo.execute();
    }

    public void test_configuration_install_all_latest_drivers() throws Exception {
        InstallDriversMojo mojo = getMojo("src/test/resources/test-mojo-configuration-install-all-latest-drivers-pom.xml", "install-drivers");
        mojo.repositoryUrl = Thread.currentThread().getContextClassLoader().getResource("repository.json");

        mojo.execute();
    }

    public void test_raise_error_when_driver_was_not_found_in_configuration() throws Exception {
        InstallDriversMojo mojo = getMojo("src/test/resources/test-mojo-configuration-pom_not_found_driver.xml", "install-drivers");
        mojo.repositoryUrl = Thread.currentThread().getContextClassLoader().getResource("repository.json");

        try {
            mojo.execute();
            fail("should raise an exception");
        } catch (MojoExecutionException e) {
            assertEquals("Could not find driver: {\"name\":\"phantooomjs\",\"platform\":\"linux\",\"bit\":\"32\",\"version\":\"1.9.7\"}", e.getMessage());
        }
    }

    public void test_configuration_extract_phantom_j_s_driver_from_tar_bz_() throws Exception {
        InstallDriversMojo mojo = getMojo("src/test/resources/test-mojo-configuration-pom_phantomjs-extract.xml", "install-drivers");
        mojo.repositoryUrl = Thread.currentThread().getContextClassLoader().getResource("repository.json");

        mojo.execute();

        File[] files = installationDirectory.listFiles();
        assertThat(files).hasSize(2);
        assertThat(files[0]).isFile();
        assertThat(files[1]).isFile();
    }
}
