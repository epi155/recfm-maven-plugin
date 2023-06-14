package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.AccesMode;
import io.github.epi155.recfm.api.InitializeNuxMode;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class AccesModeDescription extends TypeDescription {
    public AccesModeDescription() {
        super(AccesMode.class);
    }
    public Object newInstance(Node node) {
        if (node instanceof ScalarNode) {
            ScalarNode sNode = (ScalarNode) node;
            String value = sNode.getValue();
            switch (value) {
                case "String":
                case "Str":
                    return AccesMode.String;
                case "Number":
                case "Num":
                    return AccesMode.Number;
                case "Both":
                case "All":
                    return AccesMode.Both;
                default:
                    return null;
            }
        }
        return null;
    }
}
