package p4_aas.Submodels.NetworkInfrastructure;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import p4_aas.Submodels.SwitchRuntime.SwitchCliClient;

public class FC1ControlLambda {

    private final SwitchCliClient switchCliClient;

    public FC1ControlLambda() {
        this.switchCliClient = new SwitchCliClient();
    }

    public Function<Map<String, SubmodelElement>, SubmodelElement[]> getThreshold(){
        return (args) -> {
            String command = "register_read MyIngress.threshold_reg 0";
            String result1 = switchCliClient.runCliCommand(1, command);
            String result2 = switchCliClient.runCliCommand(2, command);

            return new SubmodelElement[] {
                new Property(
                    "Output ",
                    "Switch 1" + result1 +
                    " Switch 2" + result2 
                )
            };
        };
    }
    public Function<Map<String, SubmodelElement>, SubmodelElement[]> setThreshold(Property cachedThreshold) {

        return (args) -> {

            int threshold = Integer.parseInt(
                String.valueOf(args.get("Value").getValue())
            );

            String command =
                "register_write MyIngress.threshold_reg 0 " + threshold;

            String resetCounterCommand =
                "register_write MyIngress.function_code_1_counter 0 0";

            String resetBlockedCommand =
                "register_write MyIngress.blocked 0 0";
            String result1 =
                switchCliClient.runCliCommand(1, command) + "\n" + switchCliClient.runCliCommand(1, resetCounterCommand)
    + "\n" + switchCliClient.runCliCommand(1, resetBlockedCommand);

            String result2 =
                switchCliClient.runCliCommand(2, command) + "\n" + switchCliClient.runCliCommand(2, resetCounterCommand)
    + "\n" + switchCliClient.runCliCommand(2, resetBlockedCommand);

            cachedThreshold.setValue(threshold);

            return new SubmodelElement[] {
                new Property("Output","Threshold set to" + threshold +  " Switch 1:" + result1 + " Switch 2:" + result2)
            };
        };
    }
}
