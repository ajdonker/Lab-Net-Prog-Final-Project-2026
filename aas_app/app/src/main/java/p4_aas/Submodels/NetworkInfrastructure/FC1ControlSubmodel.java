

package p4_aas.Submodels.NetworkInfrastructure;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;

import p4_aas.StaticProperties;
import p4_aas.Submodels.AbstractSubmodel;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class FC1ControlSubmodel extends AbstractSubmodel {

    private final FC1ControlLambda lambda = new FC1ControlLambda();
    private final Property threshold = new Property("Threshold",10);
    Operation setThreshold = new Operation("SetThreshold");

    public FC1ControlSubmodel(){
        super();
    }
    @Override
    public List<Submodel> createSubmodel(){
        Submodel submodel = new Submodel();

        submodel.setIdShort("FC1ControlSubmodel");
        submodel.addSubmodelElement(threshold);
        submodel.addSubmodelElement(setThreshold());
        submodel.addSubmodelElement(getThreshold());

       return List.of(submodel);
    }
    private Operation getThreshold(){
        Operation op = new Operation("GetThreshold");
        op.setOutputVariables(getUtils().getOperationVariables(1, "Output"));
        op.setWrappedInvokable((args) -> {
            return new SubmodelElement[] {
                new Property("Output", threshold.getValue())
            };
        });
        return op; 
    }
    private Operation setThreshold() {
        Operation op = new Operation("SetThreshold");

        Map<String, ValueType> inputs = new LinkedHashMap<>();
        inputs.put("Value", ValueType.Integer);

        op.setInputVariables(getUtils().getCustomInputVariables(inputs));
        op.setOutputVariables(getUtils().getOperationVariables(1, "Output"));

        op.setWrappedInvokable(lambda.setThreshold());

        return op;
    }

}
