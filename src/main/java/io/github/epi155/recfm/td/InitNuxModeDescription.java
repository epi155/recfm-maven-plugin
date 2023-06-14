package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.InitializeNuxMode;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class InitNuxModeDescription extends TypeDescription {
    public InitNuxModeDescription() {
        super(InitializeNuxMode.class);
    }
    public Object newInstance(Node node) {
        if (node instanceof ScalarNode) {
            ScalarNode sNode = (ScalarNode) node;
            String value = sNode.getValue();
            switch (value) {
                case "Spaces":
                case "Space":
                    return InitializeNuxMode.Spaces;
                case "Zeroes":
                case "Zero":
                    return InitializeNuxMode.Zeroes;
                default:
                    return null;
            }
        }
        return null;
    }
}
