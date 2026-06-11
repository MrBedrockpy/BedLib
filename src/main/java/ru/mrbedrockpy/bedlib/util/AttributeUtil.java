package ru.mrbedrockpy.bedlib.util;

import lombok.experimental.UtilityClass;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;

@UtilityClass
public class AttributeUtil {

    public double getAttributeValue(Attributable attributable, Attribute attribute) {
        AttributeInstance attributeInstance = attributable.getAttribute(attribute);
        if (attributeInstance != null) return attributeInstance.getBaseValue();
        return 0;
    }

    public void setAttributeValue(Attributable attributable, Attribute attribute, int value) {
        AttributeInstance attributeInstance = attributable.getAttribute(attribute);
        if (attributeInstance != null) attributeInstance.setBaseValue(value);
    }

}
