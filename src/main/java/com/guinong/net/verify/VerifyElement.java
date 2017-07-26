package com.guinong.net.verify;

import com.guinong.net.verify.annotation.AnnotationUtils;
import com.guinong.net.verify.check.IValueCheck;
import com.guinong.net.verify.check.NotNullCheck;
import com.guinong.net.verify.check.NumberRangeCheck;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author csn
 * @date 2017/7/26 0026 10:13
 * @content
 */
public class VerifyElement {

    class Element {

        private final Annotation annotation;
        private final IValueCheck valueCheck;

        public Element(Annotation annotation, IValueCheck valueCheck) {
            this.annotation = annotation;
            this.valueCheck = valueCheck;
        }

        public Annotation getAnnotation() {
            return annotation;
        }

        public IValueCheck getValueCheck() {
            return valueCheck;
        }
    }

    private final Field field;
    private final Set<Element> elementSet;

    public VerifyElement(Field field, Annotation[] annotations) {
        field.setAccessible(true);
        this.field = field;
        this.elementSet = new HashSet<>();
        if (annotations != null && annotations.length > 0) {
            for (Annotation ann : annotations) {
                AnnotationUtils.check(field, ann);
                if (ann instanceof NotNull) {
                    this.elementSet.add(new Element(ann, new NotNullCheck()));
                } else if (ann instanceof NumberRange) {
                    this.elementSet.add(new Element(ann, new NumberRangeCheck()));
                }
            }
        }
    }

    public Field getField() {
        return field;
    }

    public void verify(Object requestBean) {
        if (requestBean == null) {
            return;
        }
        try {
            Object value = field.get(requestBean);
            for (Element el : elementSet) {
                el.valueCheck.validate(this.field, el.annotation, value);
            }
        } catch (IllegalAccessException e) {
            return;
        }
    }
}
