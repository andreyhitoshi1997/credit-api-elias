/*
 * MIT License
 *
 * Copyright (c) 2023 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.eliasnogueira.credit.tests;

import com.eliasnogueira.credit.BaseAPI;
import com.eliasnogueira.credit.data.changeless.RestrictionsData;
import com.eliasnogueira.credit.data.factory.RestrictionDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static java.text.MessageFormat.format;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;

class RestrictionsIntegrationTest extends BaseAPI {

    private final RestrictionDataFactory restrictionDataFactory = new RestrictionDataFactory();

    @Test
    @DisplayName("Should query a CPF without restriction")
    void cpfWithNoRestriction() {
        given()
            .pathParam(RestrictionsData.CPF, restrictionDataFactory.cpfWithoutRestriction()).
        when()
            .get(RestrictionsData.GET_RESTRICTIONS).
        then()
            .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Should query a CPF with restriction")
    void cpfWithRestriction() {
        String cpfWithRestriction = restrictionDataFactory.cpfWithRestriction();

        given()
            .pathParam(RestrictionsData.CPF, cpfWithRestriction).
        when()
            .get(RestrictionsData.GET_RESTRICTIONS).
        then()
            .statusCode(SC_OK)
            .body("message", is(format("CPF {0} has a restriction", cpfWithRestriction)));
    }
}
