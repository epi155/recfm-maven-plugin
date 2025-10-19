package io.github.epi155.recfm.ctor;

import io.github.epi155.recfm.api.*;
import io.github.epi155.recfm.proxy.EmbProxy;
import io.github.epi155.recfm.proxy.GrpProxy;
import io.github.epi155.recfm.proxy.OccProxy;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class RFConstructor extends Constructor {
    private final CodeFactory factory;

    public RFConstructor(Class<?> theRoot, LoaderOptions loadingConfig, CodeFactory factory) {
        super(theRoot, loadingConfig);
        this.factory = factory;

        // Associo il tag !foo a un costruttore custom
        this.yamlConstructors.put(new org.yaml.snakeyaml.nodes.Tag("!Abc"), this.new ConstructAbc());
        this.yamlConstructors.put(new org.yaml.snakeyaml.nodes.Tag("!Num"), this.new ConstructNum());
        this.yamlConstructors.put(new org.yaml.snakeyaml.nodes.Tag("!Fil"), this.new ConstructFil());
        this.yamlConstructors.put(new org.yaml.snakeyaml.nodes.Tag("!Val"), this.new ConstructVal());

        this.yamlConstructors.put(new org.yaml.snakeyaml.nodes.Tag("!Grp"), this.new ConstructGrp());
        this.yamlConstructors.put(new org.yaml.snakeyaml.nodes.Tag("!Occ"), this.new ConstructOcc());

        this.yamlConstructors.put(new org.yaml.snakeyaml.nodes.Tag("!GRP"), this.new ConstructGRP());
        this.yamlConstructors.put(new org.yaml.snakeyaml.nodes.Tag("!OCC"), this.new ConstructOCC());
        this.yamlConstructors.put(new org.yaml.snakeyaml.nodes.Tag("!Emb"), this.new ConstructEmb());

    }
    private class ConstructAbc implements Construct {

        @Override
        public Object construct(Node node) {
            AbcModel target = factory.newAbcModel();

            if (node instanceof MappingNode) {
                Map<Object, Object> values = constructMapping((MappingNode) node);
                if (values.containsKey("name")) safeSet(values, "name", String.class, target::setName);
                if (values.containsKey("length")) safeSet(values, "length", Integer.class, target::setLength);
                if (values.containsKey("override")) safeSet(values, "override", Boolean.class, target::setOverride);
                if (values.containsKey("offset")) safeSet(values, "offset", Integer.class, target::setOffset);
                if (values.containsKey("check"))  safeSet(values, "check", target::setCheck, CheckAbc::valueOf);
                if (values.containsKey("normalize")) safeSet(values, "normalize", target::setNormalize, NormalizeAbcMode::valueOf);
                if (values.containsKey("onOverflow")) safeSet(values, "onOverflow", target::setOnOverflow, OverflowAction::valueOf);
                if (values.containsKey("onUnderflow")) safeSet(values, "onUnderflow", target::setOnUnderflow, UnderflowAction::valueOf);
                if (values.containsKey("checkGetter")) safeSet(values, "checkGetter", Boolean.class, target::setCheckGetter);
                if (values.containsKey("checkSetter")) safeSet(values, "checkSetter", Boolean.class, target::setCheckSetter);

                if (values.containsKey("at")) safeSet(values, "at", Integer.class, target::setOffset);
                if (values.containsKey("len")) safeSet(values, "len", Integer.class, target::setLength);
                if (values.containsKey("chk")) safeSet(values, "chk", target::setCheck, CheckAbc::valueOf);
                if (values.containsKey("ovr")) safeSet(values, "ovr", Boolean.class, target::setOverride);
                if (values.containsKey("ovf")) safeSet(values, "ovf", target::setOnOverflow, OverflowAction::valueOf);
                if (values.containsKey("unf")) safeSet(values, "unf", target::setOnUnderflow, UnderflowAction::valueOf);
                if (values.containsKey("nrm")) safeSet(values, "nrm", target::setNormalize, NormalizeAbcMode::valueOf);
                if (values.containsKey("get")) safeSet(values, "get", Boolean.class, target::setCheckGetter);
                if (values.containsKey("set")) safeSet(values, "set", Boolean.class, target::setCheckSetter);

            } else if (node instanceof SequenceNode) {
                boolean isFilled = false;
                List<?> values = constructSequence((SequenceNode) node);
                int argc = values.size();
                if (argc == 3) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    Object arg2 = values.get(2);
                    if (arg0 instanceof String && arg1 instanceof Integer && arg2 instanceof Integer) {
                        target.setName((String) arg0);
                        target.setOffset((Integer) arg1);
                        target.setLength((Integer) arg2);
                        isFilled = true;
                    } else if (arg0 instanceof String && arg1 instanceof Integer &&
                            arg2 instanceof String && enumExists(CheckAbc.class, (String) arg2)) {
                        target.setName((String) arg0);
                        target.setLength((Integer) arg1);
                        target.setCheck(CheckAbc.valueOf((String) arg2));
                        isFilled = true;
                    } else if (arg0 instanceof String && arg1 instanceof Integer &&
                            arg2 instanceof String && enumExists(NormalizeAbcMode.class, (String) arg2)) {
                        target.setName((String) arg0);
                        target.setLength((Integer) arg1);
                        target.setNormalize(NormalizeAbcMode.valueOf((String) arg2));
                        isFilled = true;
                    }
                }
                if (!isFilled) {
                    if (argc > 0) safeSet(values, 0, String.class, target::setName);
                    if (argc > 1) safeSet(values, 1, Integer.class, target::setLength);
                    if (argc > 2) safeSet(values, 2, Boolean.class, target::setOverride);
                    if (argc > 3) safeSet(values, 3, Integer.class, target::setOffset);
                    if (argc > 4) safeSet(values, 4, target::setCheck, CheckAbc::valueOf);
                    if (argc > 5) safeSet(values, 5, target::setNormalize, NormalizeAbcMode::valueOf);
                    if (argc > 6) safeSet(values, 6, target::setOnOverflow, OverflowAction::valueOf);
                    if (argc > 7) safeSet(values, 7, target::setOnUnderflow, UnderflowAction::valueOf);
                    if (argc > 8) safeSet(values, 8, Boolean.class, target::setCheckGetter);
                    if (argc > 9) safeSet(values, 9, Boolean.class, target::setCheckSetter);
                }
            }
            return target;
        }

        @Override
        public void construct2ndStep(Node node, Object o) {
            // non serve in questo caso
        }
        private <T> void safeSet(Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Abc", values, label, type, setter);
        }
        private <R> void safeSet(Map<Object, Object> values, String label, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Abc", values, label, String.class, setter, map);
        }

        private <T> void safeSet(List<?> values, int i, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Abc", values, i, type, setter);
        }
        private <R> void safeSet(List<?> values, int i, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Abc", values, i, String.class, setter, map);
        }
    }

    private <T> void safeSet(String tag, Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
        Object arg = values.get(label);
        if (type.isInstance(arg)) {
            //noinspection unchecked
            setter.accept((T) arg);
        } else {
            String argv = argvOf(values, label);
            throw new IllegalArgumentException(tag +"{"+ argv +"}: Invalid value type for key @"+ label +", expected <"+type.getSimpleName()+"> provided <" + arg.getClass().getSimpleName() + ">");
        }
    }

    private <T> void safeSet(String tag, List<?> values, int i, Class<T> type, Consumer<T> setter) {
        Object arg = values.get(i);
        if (type.isInstance(arg)) {
            //noinspection unchecked
            setter.accept((T) arg);
        } else {
            String argv = argvOf(values, i);
            throw new IllegalArgumentException(tag +"["+ argv +"]: Invalid argument #"+ (i+1) +" type, expected <"+type.getSimpleName()+"> provided <" + arg.getClass().getSimpleName() + ">");
        }
    }

    private String argvOf(Map<Object, Object> values, String label) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Object, Object> e: values.entrySet()) {
            Object key = e.getKey();
            Object value = e.getValue();
            if (sb.length()>0) sb.append(",");
            if (label.equals(key)) sb.append("**");
            sb.append(key).append(":");
            if (value instanceof String) {
                String s = value.toString();
                sb.append('"').append(StringEscaper.escape(s)).append('"');
            } else {
                sb.append(value);
            }
            if (label.equals(key)) sb.append("**");
        }
        return sb.toString();
    }

    private String argvOf(List<?> values, int i) {
        StringBuilder sb = new StringBuilder();
        for(int k=0; k<values.size(); k++) {
            Object value = values.get(k);
            if (sb.length()>0) sb.append(",");
            if (k==i) sb.append("**");
            if (value instanceof String) {
                String s = value.toString();
                sb.append('"').append(StringEscaper.escape(s)).append('"');
            } else {
                sb.append(value);
            }
            if (k==i) sb.append("**");
        }
        return sb.toString();
    }

    private <T,R> void safeSet(String tag, Map<Object, Object> values, String label, Class<T> type, Consumer<R> setter, Function<T,R> map) {
        Object arg = values.get(label);
        if (type.isInstance(arg)) {
            //noinspection unchecked
            setter.accept(map.apply((T) arg));
        } else {
            String argv = argvOf(values, label);
            throw new IllegalArgumentException(tag +"["+ argv +"]: Invalid value type for key @"+ label +", expected <"+type.getSimpleName()+"> provided<>" + arg.getClass().getSimpleName() + ">");
        }
    }

    private <T,R> void safeSet(String tag, List<?> values, int i, Class<T> type, Consumer<R> setter, Function<T,R> map) {
        Object arg = values.get(i);
        if (type.isInstance(arg)) {
            //noinspection unchecked
            setter.accept(map.apply((T) arg));
        } else {
            String argv = argvOf(values, i);
            throw new IllegalArgumentException(tag +"["+ argv +"]: Invalid argument #"+ (i+1) +" type, expected <"+type.getSimpleName()+"> provided<>" + arg.getClass().getSimpleName() + ">");
        }
    }

    private static <E extends Enum<E>> boolean enumExists(Class<E> enumClass, String name) {
        for(E e: enumClass.getEnumConstants()) {
            if (e.name().equals(name)) return true;
        }
        return false;
    }

    private static final Map<String, AccesMode> ACCES_MODE_MAP;
    private static final Map<String, WordWidth> WORD_WIDTH_MAP;
    static {
        Map<String, AccesMode> amMap = new HashMap<>();
        amMap.put("String", AccesMode.String);
        amMap.put("Str", AccesMode.String);
        amMap.put("Number", AccesMode.Number);
        amMap.put("Num", AccesMode.Number);
        amMap.put("Both", AccesMode.Both);
        amMap.put("All", AccesMode.Both);
        ACCES_MODE_MAP = Collections.unmodifiableMap(amMap);

        Map<String, WordWidth> wwMap = new HashMap<>();
        wwMap.put("Byte", WordWidth.Byte);
        wwMap.put("byte", WordWidth.Byte);
        wwMap.put("1", WordWidth.Byte);
        wwMap.put("Short", WordWidth.Short);
        wwMap.put("short", WordWidth.Short);
        wwMap.put("2", WordWidth.Short);
        wwMap.put("Int", WordWidth.Int);
        wwMap.put("int", WordWidth.Int);
        wwMap.put("4", WordWidth.Int);
        wwMap.put("Long", WordWidth.Long);
        wwMap.put("long", WordWidth.Long);
        wwMap.put("8", WordWidth.Long);
        WORD_WIDTH_MAP = Collections.unmodifiableMap(wwMap);
    }
    private class ConstructNum implements Construct {
        @Override
        public Object construct(Node node) {
            NumModel target = factory.newNumModel();

            if (node instanceof MappingNode) {
                Map<Object, Object> values = constructMapping((MappingNode) node);
                if (values.containsKey("name")) safeSet(values, "name", String.class, target::setName);
                if (values.containsKey("length")) safeSet(values, "length", Integer.class, target::setLength);
                if (values.containsKey("override")) safeSet(values, "override", Boolean.class, target::setOverride);
                if (values.containsKey("access")) safeSet(values, "access", target::setAccess, ACCES_MODE_MAP::get);
                if (values.containsKey("offset")) safeSet(values, "offset", Integer.class, target::setOffset);
                if (values.containsKey("normalize")) safeSet(values, "normalize", target::setNormalize, NormalizeNumMode::valueOf);
                if (values.containsKey("onOverflow")) safeSet(values, "onOverflow", target::setOnOverflow, OverflowAction::valueOf);
                if (values.containsKey("onUnderflow")) safeSet(values, "onUnderflow", target::setOnUnderflow, UnderflowAction::valueOf);
                if (values.containsKey("wordWidth")) safeSet(values, "wordWidth", target::setWordWidth, WORD_WIDTH_MAP::get);

                if (values.containsKey("at")) safeSet(values, "at", Integer.class, target::setOffset);
                if (values.containsKey("len")) safeSet(values, "len", Integer.class, target::setLength);
                if (values.containsKey("ovr")) safeSet(values, "ovr", Boolean.class, target::setOverride);
                if (values.containsKey("ovf")) safeSet(values, "ovf", target::setOnOverflow, OverflowAction::valueOf);
                if (values.containsKey("unf")) safeSet(values, "unf", target::setOnUnderflow, UnderflowAction::valueOf);
                if (values.containsKey("nrm")) safeSet(values, "nrm", target::setNormalize, NormalizeNumMode::valueOf);
                if (values.containsKey("acc")) safeSet(values, "acc", target::setAccess, ACCES_MODE_MAP::get);
                if (values.containsKey("wid")) safeSet(values, "wid", target::setWordWidth, WORD_WIDTH_MAP::get);

            } else if (node instanceof SequenceNode) {
                boolean isFilled = false;
                List<?> values = constructSequence((SequenceNode) node);
                int argc = values.size();
                if (argc == 2) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    if (arg0 instanceof String && arg1 instanceof Integer) {
                        target.setName((String) arg0);
                        target.setLength((Integer) arg1);
                        isFilled = true;
                    }
                } else if (argc == 3) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    Object arg2 = values.get(2);
                    if (arg0 instanceof String && arg1 instanceof Integer && arg2 instanceof Integer) {
                        target.setName((String) arg0);
                        target.setOffset((Integer) arg1);
                        target.setLength((Integer) arg2);
                        isFilled = true;
                    } else if (arg0 instanceof String && arg1 instanceof Integer && arg2 instanceof Boolean) {
                        target.setName((String) arg0);
                        target.setLength((Integer) arg1);
                        target.setOverride((Boolean) arg2);
                        isFilled = true;
                    } else if (arg0 instanceof String && arg1 instanceof Integer &&
                            arg2 instanceof String && enumExists(NormalizeNumMode.class, (String) arg2)) {
                        target.setName((String) arg0);
                        target.setLength((Integer) arg1);
                        target.setNormalize(NormalizeNumMode.valueOf((String) arg2));
                        isFilled = true;
                    } else if (arg0 instanceof String && arg1 instanceof Integer &&
                            arg2 instanceof String && ACCES_MODE_MAP.containsKey(arg2)) {
                        target.setName((String) arg0);
                        target.setLength((Integer) arg1);
                        target.setAccess(ACCES_MODE_MAP.get((String) arg2));
                        isFilled = true;
                    }
                }
                if (!isFilled) {
                    if (argc > 0) safeSet(values, 0, String.class, target::setName);
                    if (argc > 1) safeSet(values, 1, Integer.class, target::setLength);
                    if (argc > 2) safeSet(values, 2, Boolean.class, target::setOverride);
                    if (argc > 3) safeSet(values, 3, Integer.class, target::setOffset);
                    if (argc > 4) safeSet(values, 4, target::setAccess, ACCES_MODE_MAP::get);
                    if (argc > 5) safeSet(values, 5, target::setNormalize, NormalizeNumMode::valueOf);
                    if (argc > 6) safeSet(values, 6, target::setOnOverflow, OverflowAction::valueOf);
                    if (argc > 7) safeSet(values, 7, target::setOnUnderflow, UnderflowAction::valueOf);
                    if (argc > 8) safeSet(values, 8, target::setWordWidth, WORD_WIDTH_MAP::get);
                }
            }
            return target;
        }

        @Override
        public void construct2ndStep(Node node, Object o) {
            // non serve in questo caso
        }
        private <T> void safeSet(Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Num", values, label, type, setter);
        }
        private <R> void safeSet(Map<Object, Object> values, String label, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Num", values, label, String.class, setter, map);
        }
        private <T> void safeSet(List<?> values, int i, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Num", values, i, type, setter);
        }
        private <R> void safeSet(List<?> values, int i, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Num", values, i, String.class, setter, map);
        }
    }

    private class ConstructFil implements Construct {
        @Override
        public Object construct(Node node) {
            FilModel target = factory.newFilModel();

            if (node instanceof MappingNode) {
                Map<Object, Object> values = constructMapping((MappingNode) node);
                if (values.containsKey("length")) safeSet(values, "length", Integer.class, target::setLength);
                if (values.containsKey("offset")) safeSet(values, "offset", Integer.class, target::setOffset);
                if (values.containsKey("fill")) safeSet(values, "fill", target::setFill, s -> s.charAt(0));

                if (values.containsKey("at")) safeSet(values, "at", Integer.class, target::setOffset);
                if (values.containsKey("len")) safeSet(values, "len", Integer.class, target::setLength);

            } else if (node instanceof SequenceNode) {
                boolean isFilled = false;
                List<?> values = constructSequence((SequenceNode) node);
                int argc = values.size();
                if (argc == 2) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    if (arg0 instanceof Integer && arg1 instanceof Integer) {
                        target.setOffset((Integer) arg0);
                        target.setLength((Integer) arg1);
                        isFilled = true;
                    }
                }
                if (!isFilled) {
                    if (argc > 0) safeSet(values, 0, Integer.class, target::setLength);
                    if (argc > 1) safeSet(values, 1, Integer.class, target::setOffset);
                    if (argc > 2) safeSet(values, 2, target::setFill, s -> s.charAt(0));
                }
            }
            return target;
        }

        @Override
        public void construct2ndStep(Node node, Object o) {
            // non serve in questo caso
        }
        private <T> void safeSet(Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Fil", values, label, type, setter);
        }
        private <R> void safeSet(Map<Object, Object> values, String label, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Fil", values, label, String.class, setter, map);
        }
        private <T> void safeSet(List<?> values, int i, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Fil", values, i, type, setter);
        }
        private <R> void safeSet(List<?> values, int i, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Fil", values, i, String.class, setter, map);
        }
    }

    private class ConstructVal implements Construct {
        @Override
        public Object construct(Node node) {
            ValModel target = factory.newValModel();

            if (node instanceof MappingNode) {
                Map<Object, Object> values = constructMapping((MappingNode) node);
                if (values.containsKey("length")) safeSet(values, "length", Integer.class, target::setLength);
                if (values.containsKey("offset")) safeSet(values, "offset", Integer.class, target::setOffset);
                if (values.containsKey("value")) safeSet(values, "value", String.class, target::setValue);

                if (values.containsKey("at")) safeSet(values, "at", Integer.class, target::setOffset);
                if (values.containsKey("len")) safeSet(values, "len", Integer.class, target::setLength);
                if (values.containsKey("val")) safeSet(values, "val", String.class, target::setValue);

            } else if (node instanceof SequenceNode) {
                boolean isFilled = false;
                List<?> values = constructSequence((SequenceNode) node);
                int argc = values.size();
                if (argc == 1) {
                    Object arg0 = values.get(0);
                    if (arg0 instanceof String) {
                        String s = (String) arg0;
                        target.setValue(s);
                        target.setLength(s.length());
                        isFilled = true;
                    }
                } else if (argc == 2) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    if (arg0 instanceof String && arg1 instanceof Integer) {
                        target.setValue((String) arg0);
                        target.setLength((Integer) arg1);
                        isFilled = true;
                    }
                } else if (argc == 3) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    Object arg2 = values.get(2);
                    if (arg0 instanceof String && arg1 instanceof Integer && arg2 instanceof Integer) {
                        target.setValue((String) arg0);
                        target.setOffset((Integer) arg1);
                        target.setLength((Integer) arg2);
                        isFilled = true;
                    }
                }
                if (!isFilled) {
                    if (argc > 0) safeSet(values, 0, String.class, target::setValue);
                    if (argc > 1) safeSet(values, 1, Integer.class, target::setLength);
                    if (argc > 2) safeSet(values, 2, Integer.class, target::setOffset);
                }
            }
            return target;
        }

        @Override
        public void construct2ndStep(Node node, Object o) {
            // non serve in questo caso
        }
        private <T> void safeSet(Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Val", values, label, type, setter);
        }
        private <T> void safeSet(List<?> values, int i, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Val", values, i, type, setter);
        }
    }

    private class ConstructGrp implements Construct {
        @Override
        public Object construct(Node node) {
            GrpModel target = factory.newGrpModel();

            if (node instanceof MappingNode) {
                Map<Object, Object> values = constructMapping((MappingNode) node);
                if (values.containsKey("name")) safeSet(values, "name", String.class, target::setName);
                if (values.containsKey("length")) safeSet(values, "length", Integer.class, target::setLength);
                if (values.containsKey("override")) safeSet(values, "override", Boolean.class, target::setOverride);
                if (values.containsKey("offset")) safeSet(values, "offset", Integer.class, target::setOffset);
                if (values.containsKey("fields")) safeSet(values, "fields", List.class, target::setFields);
//                    target.setFields((List<FieldModel>) values.get("fields"));

                if (values.containsKey("at")) safeSet(values, "at", Integer.class, target::setOffset);
                if (values.containsKey("len")) safeSet(values, "len", Integer.class, target::setLength);
                if (values.containsKey("ovr")) safeSet(values, "ovr", Boolean.class, target::setOverride);
                if (values.containsKey("flds")) safeSet(values, "flds", List.class, target::setFields);

            } else if (node instanceof SequenceNode) {
                boolean isFilled = false;
                List<?> values = constructSequence((SequenceNode) node);
                int argc = values.size();
                if (argc == 2) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    if (arg0 instanceof String && arg1 instanceof List) {
                        target.setName((String) arg0);
                        //noinspection unchecked
                        target.setFields((List<FieldModel>) arg1);
                        isFilled = true;
                    }
                } else if (argc == 3) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    Object arg2 = values.get(2);
                    if (arg0 instanceof String && arg1 instanceof Integer && arg2 instanceof List) {
                        target.setName((String) arg0);
                        target.setLength((Integer) arg1);
                        //noinspection unchecked
                        target.setFields((List<FieldModel>) arg2);
                        isFilled = true;
                    }
                } else if (argc == 4) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    Object arg2 = values.get(2);
                    Object arg3 = values.get(3);
                    if (arg0 instanceof String && arg1 instanceof Integer
                            && arg2 instanceof Integer && arg3 instanceof List) {
                        target.setName((String) arg0);
                        target.setOffset((Integer) arg1);
                        target.setLength((Integer) arg2);
                        //noinspection unchecked
                        target.setFields((List<FieldModel>) arg3);
                        isFilled = true;
                    } else if (arg0 instanceof String && arg1 instanceof Integer
                            && arg2 instanceof Boolean && arg3 instanceof List) {
                        target.setName((String) arg0);
                        target.setLength((Integer) arg1);
                        target.setOverride((Boolean) arg2);
                        //noinspection unchecked
                        target.setFields((List<FieldModel>) arg3);
                        isFilled = true;
                    }
                }
                if (!isFilled) {
                    if (argc > 0) safeSet(values, 0, String.class, target::setName);
                    if (argc > 1) safeSet(values, 1, List.class, target::setFields);
                    if (argc > 2) safeSet(values, 2, Integer.class, target::setLength);
                    if (argc > 3) safeSet(values, 3, Boolean.class, target::setOverride);
                    if (argc > 4) safeSet(values, 4, Integer.class, target::setOffset);
                }
            }
            return target;
        }

        @Override
        public void construct2ndStep(Node node, Object o) {
            // non serve in questo caso
        }
        private <T> void safeSet(Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Grp", values, label, type, setter);
        }
        private <R> void safeSet(Map<Object, Object> values, String label, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Grp", values, label, String.class, setter, map);
        }
        private <T> void safeSet(List<?> values, int i, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Grp", values, i, type, setter);
        }
        private <R> void safeSet(List<?> values, int i, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Grp", values, i, String.class, setter, map);
        }
    }

    private class ConstructOcc implements Construct {
        @Override
        public Object construct(Node node) {
            OccModel target = factory.newOccModel();

            if (node instanceof MappingNode) {
                Map<Object, Object> values = constructMapping((MappingNode) node);
                if (values.containsKey("name")) safeSet(values, "name", String.class, target::setName);
                if (values.containsKey("length")) safeSet(values, "length", Integer.class, target::setLength);
                if (values.containsKey("override")) safeSet(values, "override", Boolean.class, target::setOverride);
                if (values.containsKey("offset")) safeSet(values, "offset", Integer.class, target::setOffset);
                if (values.containsKey("times")) safeSet(values, "times", Integer.class, target::setTimes);
                if (values.containsKey("fields")) safeSet(values, "fields", List.class, target::setFields);

                if (values.containsKey("at")) safeSet(values, "at", Integer.class, target::setOffset);
                if (values.containsKey("len")) safeSet(values, "len", Integer.class, target::setLength);
                if (values.containsKey("ovr")) safeSet(values, "ovr", Boolean.class, target::setOverride);
                if (values.containsKey("x")) safeSet(values, "x", Integer.class, target::setTimes);
                if (values.containsKey("flds")) safeSet(values, "flds", List.class, target::setFields);

            } else if (node instanceof SequenceNode) {
                boolean isFilled = false;
                List<?> values = constructSequence((SequenceNode) node);
                int argc = values.size();
                if (argc == 3) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    Object arg2 = values.get(2);
                    if (arg0 instanceof String && arg1 instanceof Integer && arg2 instanceof List) {
                        target.setName((String) arg0);
                        target.setTimes((Integer) arg1);
                        //noinspection unchecked
                        target.setFields((List<FieldModel>) arg2);
                        isFilled = true;
                    }
                } else if (argc == 4) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    Object arg2 = values.get(2);
                    Object arg3 = values.get(3);
                    if (arg0 instanceof String && arg1 instanceof Integer
                            && arg2 instanceof Integer && arg3 instanceof List) {
                        target.setName((String) arg0);
                        target.setLength((Integer) arg1);
                        target.setTimes((Integer) arg2);
                        //noinspection unchecked
                        target.setFields((List<FieldModel>) arg3);
                        isFilled = true;
                    } else if (arg0 instanceof String && arg1 instanceof Integer
                            && arg2 instanceof Boolean && arg3 instanceof List) {
                        target.setName((String) arg0);
                        target.setTimes((Integer) arg1);
                        target.setOverride((Boolean) arg2);
                        //noinspection unchecked
                        target.setFields((List<FieldModel>) arg3);
                        isFilled = true;
                    }
                }
                if (!isFilled) {
                    if (argc > 0) safeSet(values, 0, String.class, target::setName);
                    if (argc > 1) safeSet(values, 1, Integer.class, target::setTimes);
                    if (argc > 2) safeSet(values, 2, List.class, target::setFields);
                    if (argc > 3) safeSet(values, 3, Integer.class, target::setLength);
                    if (argc > 4) safeSet(values, 4, Boolean.class, target::setOverride);
                    if (argc > 5) safeSet(values, 5, Integer.class, target::setOffset);
                }
            }
            return target;
        }

        @Override
        public void construct2ndStep(Node node, Object o) {
            // non serve in questo caso
        }
        private <T> void safeSet(Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Occ", values, label, type, setter);
        }
        private <R> void safeSet(Map<Object, Object> values, String label, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Occ", values, label, String.class, setter, map);
        }
        private <T> void safeSet(List<?> values, int i, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Occ", values, i, type, setter);
        }
        private <R> void safeSet(List<?> values, int i, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Occ", values, i, String.class, setter, map);
        }
    }

    private class ConstructGRP implements Construct {
        @Override
        public Object construct(Node node) {
            GrpProxy target = new GrpProxy(factory);

            if (node instanceof MappingNode) {
                Map<Object, Object> values = constructMapping((MappingNode) node);
                if (values.containsKey("name")) safeSet(values, "name", String.class, target::setName);
                if (values.containsKey("length")) safeSet(values, "length", Integer.class, target::setLength);
                if (values.containsKey("override")) safeSet(values, "override", Boolean.class, target::setOverride);
                if (values.containsKey("offset")) safeSet(values, "offset", Integer.class, target::setOffset);
                if (values.containsKey("typedef")) safeSet(values, "typedef", TraitModel.class, target::setTypedef);
                if (values.containsKey("reference")) safeSet(values, "reference", String.class, target::setReference);

                if (values.containsKey("at")) safeSet(values, "at", Integer.class, target::setOffset);
                if (values.containsKey("len")) safeSet(values, "len", Integer.class, target::setLength);
                if (values.containsKey("ovr")) safeSet(values, "ovr", Boolean.class, target::setOverride);
                if (values.containsKey("as")) safeSet(values, "as", TraitModel.class, target::setTypedef);
                if (values.containsKey("ref")) safeSet(values, "ref", String.class, target::setReference);

            } else if (node instanceof SequenceNode) {
                boolean isFilled = false;
                List<?> values = constructSequence((SequenceNode) node);
                int argc = values.size();
                if (argc == 2) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    if (arg0 instanceof String && arg1 instanceof TraitModel) {
                        target.setName((String) arg0);
                        target.setTypedef((TraitModel) arg1);
                        isFilled = true;
                    } else if (arg0 instanceof String && arg1 instanceof String) {
                        target.setName((String) arg0);
                        target.setReference((String) arg1);
                        isFilled = true;
                    }
                }
                if (!isFilled) {
                    if (argc > 0) safeSet(values, 0, String.class, target::setName);
                    if (argc > 1) {
                        Object arg1 = values.get(1);
                        if (arg1 instanceof TraitModel) {
                            target.setTypedef((TraitModel) arg1);
                        } else if (arg1 instanceof String) {
                            target.setReference((String) arg1);
                        } else {
                            throw new IllegalArgumentException("Invalid 2nd type in GRP(String, TraitModel|String ,...) - " + arg1.getClass().getName());
                        }
                    }
                    if (argc > 2) safeSet(values, 2, Integer.class, target::setLength);
                    if (argc > 3) safeSet(values, 3, Boolean.class, target::setOverride);
                    if (argc > 4) safeSet(values, 4, Integer.class, target::setOffset);
                }
            }
            return target.getDelegate();
        }

        @Override
        public void construct2ndStep(Node node, Object o) {
            // non serve in questo caso
        }
        private <T> void safeSet(Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!GRP", values, label, type, setter);
        }
        private <R> void safeSet(Map<Object, Object> values, String label, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!GRP", values, label, String.class, setter, map);
        }
        private <T> void safeSet(List<?> values, int i, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!GRP", values, i, type, setter);
        }
        private <R> void safeSet(List<?> values, int i, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!GRP", values, i, String.class, setter, map);
        }
    }

    private class ConstructOCC implements Construct {
        @Override
        public Object construct(Node node) {
            OccProxy target = new OccProxy(factory);

            if (node instanceof MappingNode) {
                Map<Object, Object> values = constructMapping((MappingNode) node);
                if (values.containsKey("name")) safeSet(values, "name", String.class, target::setName);
                if (values.containsKey("length")) safeSet(values, "length", Integer.class, target::setLength);
                if (values.containsKey("override")) safeSet(values, "override", Boolean.class, target::setOverride);
                if (values.containsKey("offset")) safeSet(values, "offset", Integer.class, target::setOffset);
                if (values.containsKey("typedef")) safeSet(values, "typedef", TraitModel.class, target::setTypedef);
                if (values.containsKey("reference")) safeSet(values, "reference", String.class, target::setReference);
                if (values.containsKey("times")) safeSet(values, "times", Integer.class, target::setTimes);

                if (values.containsKey("at")) safeSet(values, "at", Integer.class, target::setOffset);
                if (values.containsKey("len")) safeSet(values, "len", Integer.class, target::setLength);
                if (values.containsKey("ovr")) safeSet(values, "ovr", Boolean.class, target::setOverride);
                if (values.containsKey("as")) safeSet(values, "as", TraitModel.class, target::setTypedef);
                if (values.containsKey("ref")) safeSet(values, "ref", String.class, target::setReference);
                if (values.containsKey("x")) safeSet(values, "x", Integer.class, target::setTimes);

            } else if (node instanceof SequenceNode) {
                boolean isFilled = false;
                List<?> values = constructSequence((SequenceNode) node);
                int argc = values.size();
                if (argc == 3) {
                    Object arg0 = values.get(0);
                    Object arg1 = values.get(1);
                    Object arg2 = values.get(2);
                    if (arg0 instanceof String && arg1 instanceof Integer && arg2 instanceof TraitModel) {
                        target.setName((String) arg0);
                        target.setTimes((Integer) arg1);
                        target.setTypedef((TraitModel) arg2);
                        isFilled = true;
                    } else if (arg0 instanceof String && arg1 instanceof Integer & arg2 instanceof String) {
                        target.setName((String) arg0);
                        target.setTimes((Integer) arg1);
                        target.setReference((String) arg2);
                        isFilled = true;
                    }
                }
                if (!isFilled) {
                    if (argc > 0) safeSet(values, 0, String.class, target::setName);
                    if (argc > 1) safeSet(values, 1, int.class, target::setTimes);
                    if (argc > 2) {
                        Object arg2 = values.get(2);
                        if (arg2 instanceof TraitModel) {
                            target.setTypedef((TraitModel) arg2);
                        } else if (arg2 instanceof String) {
                            target.setReference((String) arg2);
                        } else {
                            throw new IllegalArgumentException("Invalid 3rd type in String(String, int, TraitModel|String ,...) - " + arg2.getClass().getName());
                        }
                    }
                    if (argc > 3) safeSet(values, 3, Integer.class, target::setLength);
                    if (argc > 4) safeSet(values, 4, Boolean.class, target::setOverride);
                    if (argc > 5) safeSet(values, 5, Integer.class, target::setOffset);
                }
            }
            return target.getDelegate();
        }

        @Override
        public void construct2ndStep(Node node, Object o) {
            // non serve in questo caso
        }
        private <T> void safeSet(Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!OCC", values, label, type, setter);
        }
        private <R> void safeSet(Map<Object, Object> values, String label, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!OCC", values, label, String.class, setter, map);
        }
        private <T> void safeSet(List<?> values, int i, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!OCC", values, i, type, setter);
        }
        private <R> void safeSet(List<?> values, int i, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!OCC", values, i, String.class, setter, map);
        }
    }

    private class ConstructEmb implements Construct {
        @Override
        public Object construct(Node node) {
            EmbProxy target = new EmbProxy(factory);

            if (node instanceof MappingNode) {
                Map<Object, Object> values = constructMapping((MappingNode) node);
                if (values.containsKey("length")) safeSet(values, "length", Integer.class, target::setLength);
                if (values.containsKey("offset")) safeSet(values, "offset", Integer.class, target::setOffset);
                if (values.containsKey("source")) safeSet(values, "source", TraitModel.class, target::setSource);
                if (values.containsKey("reference")) safeSet(values, "reference", String.class, target::setReference);

                if (values.containsKey("at")) safeSet(values, "at", Integer.class, target::setOffset);
                if (values.containsKey("len")) safeSet(values, "len", Integer.class, target::setLength);
                if (values.containsKey("src")) safeSet(values, "src", TraitModel.class, target::setSource);
                if (values.containsKey("ref")) safeSet(values, "ref", String.class, target::setReference);

            } else if (node instanceof SequenceNode) {
                boolean isFilled = false;
                List<?> values = constructSequence((SequenceNode) node);
                int argc = values.size();
                if (argc == 1) {
                    Object arg0 = values.get(0);
                    if (arg0 instanceof TraitModel) {
                        target.setSource((TraitModel) arg0);
                        isFilled = true;
                    } else if (arg0 instanceof String) {
                        target.setReference((String) arg0);
                        isFilled = true;
                    }
                }
                if (!isFilled) {
                    if (argc > 0) {
                        Object arg0 = values.get(0);
                        if (arg0 instanceof TraitModel) {
                            target.setSource((TraitModel) arg0);
                        } else if (arg0 instanceof String) {
                            target.setReference((String) arg0);
                        } else {
                            throw new IllegalArgumentException("Invalid 1st type in Emb(TraitModel|String ,...) - " + arg0.getClass().getName());
                        }
                    }
                    if (argc > 1) safeSet(values, 1, Integer.class, target::setLength);
                    if (argc > 2) safeSet(values, 2, Integer.class, target::setOffset);
                }
            }
            return target.getDelegate();
        }

        @Override
        public void construct2ndStep(Node node, Object o) {
            // non serve in questo caso
        }
        private <T> void safeSet(Map<Object, Object> values, String label, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Emb", values, label, type, setter);
        }
        private <R> void safeSet(Map<Object, Object> values, String label, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Emb", values, label, String.class, setter, map);
        }
        private <T> void safeSet(List<?> values, int i, Class<T> type, Consumer<T> setter) {
            RFConstructor.this.safeSet("!Emb", values, i, type, setter);
        }
        private <R> void safeSet(List<?> values, int i, Consumer<R> setter, Function<String,R> map) {
            RFConstructor.this.safeSet("!Emb", values, i, String.class, setter, map);
        }
    }
}
