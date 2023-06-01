package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.InitializeNuxMode;
import io.github.epi155.recfm.api.WordWidth;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;

public class WordWidthDescription extends TypeDescription {
    public WordWidthDescription() {
        super(WordWidth.class);
    }
    public Object newInstance(Node node) {
        if (node instanceof ScalarNode) {
            ScalarNode sNode = (ScalarNode) node;
            String value = sNode.getValue();
            switch (value) {
                case "W1":
                case "1":
                case "8-bit":
                    return WordWidth.W1;
                case "W2":
                case "2":
                case "16-bit":
                    return WordWidth.W2;
                case "W4":
                case "4":
                case "32-bit":
                    return WordWidth.W4;
                case "W8":
                case "8":
                case "64-bit":
                    return WordWidth.W8;
                default:
                    return null;
            }
        }
        return null;
    }
}
