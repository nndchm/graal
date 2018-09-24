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
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>

int string_arg(const char *str) {
    return atof(str);
}

const char *string_ret_const() {
    return "Hello, World!";
}

struct dynamic_string {
    int magic;
    char str[16];
};

char *string_ret_dynamic(int nr) {
    struct dynamic_string *alloc = malloc(sizeof(*alloc));
    alloc->magic = nr;
    snprintf(alloc->str, sizeof(alloc->str), "%d", nr);
    return alloc->str;
}

// wrapper around "free" that has a return value that can be verified
int free_dynamic_string(char *str) {
    struct dynamic_string *dynamic = NULL;
    intptr_t offset = dynamic->str - (char *) dynamic;
    dynamic = (struct dynamic_string *) (str - offset);
    int magic = dynamic->magic;
    free(dynamic);
    return magic;
}

int string_callback(int (*str_arg)(const char *), char *(*str_ret)()) {
    int ret;
    char *str = str_ret();
    if (str != NULL && strcmp(str, "Hello, Native!") == 0) {
        ret = str_arg("Hello, Truffle!");
    } else {
        ret = 0;
    }
    free(str);
    return ret;
}

const char *native_string_callback(const char *(*str_ret)()) {
    const char *str = str_ret();
    if (str == NULL) {
        return "null";
    } else if (str == string_ret_const()) {
        return "same";
    } else {
        return "different";
    }
}
