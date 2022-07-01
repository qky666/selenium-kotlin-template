package cucumber

import io.cucumber.testng.CucumberOptions

@CucumberOptions(
    features = ["src/test/resources/features"],
    glue = ["cucumber.steps"],
    plugin = ["pretty", "json:target/test-output/json/cucumber.json", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "rerun:build/test-results/cucumber/failed_scenarios.txt"]
)
class CucumberRunnerTest : BaseCucumberTestRunner()
