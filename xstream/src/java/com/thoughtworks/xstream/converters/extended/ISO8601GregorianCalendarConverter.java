/*
 * Copyright (C) 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2011, 2013, 2014, 2015, 2016, 2017, 2018 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 *
 * Created on 03. October 2005 by Joerg Schaible
 */
package com.thoughtworks.xstream.converters.extended;

import java.lang.reflect.InvocationTargetException;
import java.util.GregorianCalendar;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.core.JVM;


/**
 * A converter for {@link GregorianCalendar} conforming to the ISO8601 standard.
 * <p>
 * The converter will always serialize the calendar value in UTC and deserialize it to a value in the current default
 * time zone.
 * </p>
 *
 * @author Mauro Talevi
 * @author J&ouml;rg Schaible
 * @see <a href="http://www.iso.org/iso/home/store/catalogue_ics/catalogue_detail_ics.htm?csnumber=40874">ISO 8601</a>
 * @since 1.1.3
 */
public class ISO8601GregorianCalendarConverter extends AbstractSingleValueConverter {
    private final SingleValueConverter converter;

    public ISO8601GregorianCalendarConverter() {
        SingleValueConverter svConverter = null;
        final Class<? extends SingleValueConverter> type = JVM.<SingleValueConverter>loadClassForName(JVM.isVersion(8)
            ? "com.thoughtworks.xstream.core.util.ISO8601JavaTimeConverter"
            : "com.thoughtworks.xstream.core.util.ISO8601JodaTimeConverter");
        try {
            svConverter = type.getDeclaredConstructor().newInstance();
        } catch (final
                InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException e) {
            // ignore
        }
        converter = svConverter;
    }

    @Override
    public boolean canConvert(final Class<?> type) {
        return converter != null && type == GregorianCalendar.class;
    }

    @Override
    public Object fromString(final String str) {
        return converter.fromString(str);
    }

    @Override
    public String toString(final Object obj) {
        return converter.toString(obj);
    }
}
