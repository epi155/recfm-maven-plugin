package io.github.epi155.recfm.exec;

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
                case "Byte":
                case "byte":
                case "1":
                    return WordWidth.Byte;
                case "Short":
                case "short":
                case "2":
                    return WordWidth.Short;
                case "Int":
                case "int":
                case "4":
                    return WordWidth.Int;
                case "Long":
                case "long":
                case "8":
                    return WordWidth.Long;
                default:
                    return null;
            }
        }
        return null;
    }
}
