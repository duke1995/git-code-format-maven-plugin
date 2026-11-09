package com.cosium.code.format;

import static org.assertj.core.api.Assertions.assertThat;

import io.takari.maven.testing.executor.MavenRuntime;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class SingleModuleTest extends AbstractMavenModuleTest {

  public SingleModuleTest(MavenRuntime.MavenRuntimeBuilder mavenBuilder) throws Exception {
    super(mavenBuilder, "single-module", "");
  }

  @Test
  public void
      GIVEN_preliminary_goals_WHEN_installing_hooks_THEN_they_should_be_added_to_the_pre_commit_hook()
          throws Exception {
    buildMavenExecution(projectRoot())
        .withCliOptions("-Dgcf.preliminaryGoals=clean,validate")
        .execute("initialize")
        .assertErrorFreeLog();

    Path preCommitHook =
        projectRoot().resolve(".git/hooks/single-module.git-code-format.pre-commit.sh");
    String preCommitHookContent =
        new String(Files.readAllBytes(preCommitHook), StandardCharsets.UTF_8);

    assertThat(preCommitHookContent)
        .contains("clean validate com.cosium.code:git-code-format-maven-plugin:on-pre-commit");
  }
}
