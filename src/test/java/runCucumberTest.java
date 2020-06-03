import TestBased.*;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import org.testng.annotations.*;


@CucumberOptions(
        strict = true,
        features = "src/test/Features/",
        glue = {"Steps"},
        //tags = {"@RunScenario"},
        tags = {"not @broken"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        }
)

public class runCucumberTest extends mobileController {

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpTestNgCucumber() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void scenario(PickleWrapper pickles, FeatureWrapper cucumbers) throws Throwable {
        testNGCucumberRunner.runScenario(pickles.getPickle());
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideScenarios();
    }


    @AfterMethod
    public void resetAfterScenario() {
        System.out.println("TEST RUN: scenario complete");
        Utils.getDriver().resetApp();
        System.out.println("TEST RUN: app reset");
    }

    @AfterTest(alwaysRun = true)
    public void tearDownClass() {
        System.out.println("TEST RUN: all scenarios complete");
        testNGCucumberRunner.finish();
        Utils.getDriver().quit();
        System.out.println("TEST RUN: teardown complete");
    }
}
