/*
 * Copyright (c) 2017, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The Universal Permissive License (UPL), Version 1.0
 *
 * Subject to the condition set forth below, permission is hereby granted to any
 * person obtaining a copy of this software, associated documentation and/or
 * data (collectively the "Software"), free of charge and under any and all
 * copyright rights in the Software, and any and all patent rights owned or
 * freely licensable by each licensor hereunder covering either (i) the
 * unmodified Software as contributed to or provided by such licensor, or (ii)
 * the Larger Works (as defined below), to deal in both
 *
 * (a) the Software, and
 *
 * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 * one is included with the Software each a "Larger Work" to which the Software
 * is contributed by such licensors),
 *
 * without restriction, including without limitation the rights to copy, create
 * derivative works of, display, perform, and distribute the Software and make,
 * use, sell, offer for sale, import, export, have made, and have sold the
 * Software and the Larger Work(s), and to sublicense the foregoing rights on
 * either these or other terms.
 *
 * This license is subject to the following condition:
 *
 * The above copyright notice and either this complete permission notice or at a
 * minimum a reference to the UPL must be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.graalvm.nativeimage;

import org.graalvm.nativeimage.impl.RuntimeOptionsSupport;
import org.graalvm.options.OptionDescriptors;

/**
 * Used for manipulating options at run time.
 *
 * @since 1.0
 */
public final class RuntimeOptions {

    private RuntimeOptions() {
    }

    /**
     * Set the value of the option with the provided name to the new value.
     *
     * @since 1.0
     */
    public static void set(String optionName, Object value) {
        ImageSingletons.lookup(RuntimeOptionsSupport.class).set(optionName, value);
    }

    /**
     * Get the value of the option with the provided name.
     *
     * @since 1.0
     */
    public static <T> T get(String optionName) {
        return ImageSingletons.lookup(RuntimeOptionsSupport.class).get(optionName);
    }

    /**
     * Returns all available run time options.
     *
     * @since 1.0
     */
    public static OptionDescriptors getOptions() {
        return ImageSingletons.lookup(RuntimeOptionsSupport.class).getOptions();
    }

    /**
     * Runs all startup hooks that were registered during image building. Startup hooks usually
     * depend on option values, so it is recommended (but not required) that all option values are
     * set before calling this method.
     * <p>
     * Invoking this method more than once has no effect, i.e., startup hooks are only executed at
     * the first invocation.
     *
     * @since 1.0
     */
    public static void runStartupHooks() {
        ImageSingletons.lookup(RuntimeOptionsSupport.class).runStartupHooks();
    }
}
