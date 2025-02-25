package main;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
	"domain",
	"service",
	"validator",
	"controller"
})
public class AllTests {

}
