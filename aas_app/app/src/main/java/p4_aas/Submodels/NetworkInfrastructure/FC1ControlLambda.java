package p4_aas.Submodels.NetworkInfrastructure;
import java.util.Map;
import java.util.function.Function;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import p4_aas.Submodels.SwitchRuntime.SwitchCliClient;

public class FC1ControlLambda {

    private final SwitchCliClient switchCliClient;

    public FC1ControlLambda() {
        this.switchCliClient = new SwitchCliClient();
    }

    public Function<Map<String, SubmodelElement>, SubmodelElement[]> setThreshold() {

        return (args) -> {

            int threshold = Integer.parseInt(
                String.valueOf(args.get("Value").getValue())
            );

            String command =
                "register_write threshold_reg 0 " + threshold;

            String result1 =
                switchCliClient.runCliCommand(1, command);

            String result2 =
                switchCliClient.runCliCommand(2, command);

            return new SubmodelElement[] {
                new Property("Output1", result1),
                new Property("Output2", result2)
            };
        };
    }
}
