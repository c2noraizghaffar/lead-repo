package com.task.lead;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LeadCreationTests {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_AUTH = "Authorization";
    private static final String APPLICATION_JSON = "application/json";
    private static final String BEARER_TOKEN = "Bearer key";

    @BeforeClass
    public static void setupBaseURI() {
        baseURI = "https://api.tap.company/v3/connect/lead";
    }

    // Test cases for the Brand Segment Type
    @Test
    public void testSegmentTypeGov() {
        executeBrandTest("brand.segment.type.code", SegmentType.GOV.getValue(), SegmentTeam.MICRO.getValue(), SegmentType.GOV.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTypeNonProfit() {
        executeBrandTest("brand.segment.type.code", SegmentType.NON_PROFIT.getValue(), SegmentTeam.MICRO.getValue(), SegmentType.NON_PROFIT.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTypeEnterprise() {
        executeBrandTest("brand.segment.type.code", SegmentType.ENTERPRISE.getValue(), SegmentTeam.MICRO.getValue(), SegmentType.ENTERPRISE.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTypeMultinational() {
        executeBrandTest("brand.segment.type.code", SegmentType.MULTINATIONAL.getValue(), SegmentTeam.MICRO.getValue(), SegmentType.MULTINATIONAL.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTypeTech() {
        executeBrandTest("brand.segment.type.code", SegmentType.TECH.getValue(), SegmentTeam.MICRO.getValue(), SegmentType.TECH.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTypeFintech() {
        executeBrandTest("brand.segment.type.code", SegmentType.FINTECH.getValue(), SegmentTeam.MICRO.getValue(), SegmentType.FINTECH.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTypeFreelancer() {
        executeBrandTest("brand.segment.type.code", SegmentType.FREELANCER.getValue(), SegmentTeam.MICRO.getValue(), SegmentType.FREELANCER.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTypeInvalidValue() {
        executeBrandTest("brand.segment.type.code", "invalid-segment-value", SegmentTeam.MICRO.getValue(), "Invalid value provided for the 'segment.type' parameter", "code", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testSegmentTypeEmptyValue() {
        executeBrandTest("brand.segment.type.code", "", SegmentTeam.MICRO.getValue(), "Invalid value provided for the 'segment.type' parameter", "code", HttpStatus.SC_BAD_REQUEST);
    }

    // Test cases for the Brand Segment Team
    @Test
    public void testSegmentTeamMicro() {
        executeBrandTest("brand.segment.team.code", SegmentType.GOV.getValue(), SegmentTeam.MICRO.getValue(), SegmentTeam.MICRO.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTeamSmall() {
        executeBrandTest("brand.segment.team.code", SegmentType.GOV.getValue(), SegmentTeam.SMALL.getValue(), SegmentTeam.SMALL.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTeamMedium() {
        executeBrandTest("brand.segment.team.code", SegmentType.GOV.getValue(), SegmentTeam.MEDIUM.getValue(), SegmentTeam.MEDIUM.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTeamLarge() {
        executeBrandTest("brand.segment.team.code", SegmentType.GOV.getValue(), SegmentTeam.LARGE.getValue(), SegmentTeam.LARGE.getValue(), "code", HttpStatus.SC_OK);
    }

    @Test
    public void testSegmentTeamInvalidValue() {
        executeBrandTest("brand.segment.team.code", SegmentType.GOV.getValue(), "invalid-segment-team", "Invalid value provided for the 'segment.team' parameter", "code", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testSegmentTeamEmptyValue() {
        executeBrandTest("brand.segment.team.code", SegmentType.GOV.getValue(), "", "Invalid value provided for the 'segment.team' parameter", "code", HttpStatus.SC_BAD_REQUEST);
    }

    // Test cases for the User Birth Country
    @Test
    public void testValidCountryISOCode() {
        executeUserTest("user.birth.country", "US", "US", "country", HttpStatus.SC_OK);
    }

    @Test
    public void testEmptyCountryCode() {
        executeUserTest("errors[0].description", "", "ID issuing country country should be match iso2 format", "country", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testInvalidCountryCode() {
        executeUserTest("errors[0].description", "ZZ", "ID issuing country country should be match iso2 format", "country", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testNumericCountryCode() {
        executeUserTest("errors[0].description", "123", "ID issuing country country should be match iso2 format", "country", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testSingleCharCountryCode() {
        executeUserTest("errors[0].description", "U", "ID issuing country country should be match iso2 format", "country", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testThreeCharCountryCode() {
        executeUserTest("errors[0].description", "ABC", "ID issuing country country should be match iso2 format", "country", HttpStatus.SC_BAD_REQUEST);
    }

    // Test cases for the User Birth City
    @Test
    public void testCityNameWithMinLength() {
        executeUserTest("user.birth.city", "Rio", "Rio", "city", HttpStatus.SC_OK);
    }

    @Test
    public void testCityNameWithLessMinLength() {
        executeUserTest("user.birth.city", "ZZ", "ZZ", "city", HttpStatus.SC_OK);
    }

    @Test
    public void testCityNameWithMaxLength() {
        executeUserTest("user.birth.city", "San Francisco", "San Francisco", "city", HttpStatus.SC_OK);
    }

    @Test
    public void testCityNameWithSpecialCharacters() {
        executeUserTest("user.birth.city", "Street. John's", "Street. John's", "city", HttpStatus.SC_OK);
    }

    @Test
    public void testCityNameWithHyphen() {
        executeUserTest("user.birth.city", "United-kingdom", "United-kingdom", "city", HttpStatus.SC_OK);
    }

    @Test
    public void testEmptyCityName() {
        executeUserTest("errors[0].description", "", "City must be at least 3 characters long", "city", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testWrongCityName() {
        executeUserTest("errors[0].description", "000", "City must be at least 3 characters long", "city", HttpStatus.SC_BAD_REQUEST);
    }

    // Test cases for the User Birth Date
    @Test
    public void testDateOfBirthInThePast() {
        executeUserTest("user.birth.date", "1980-12-15", "1980-12-15", "date", HttpStatus.SC_OK);
    }

    @Test
    public void testDateOfBirthInFuture() {
        executeUserTest("errors[0].description", "2050-01-01", "Date of birth can't be in future", "date", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testLeapYearDate() {
        executeUserTest("user.birth.date", "2020-02-29", "2020-02-29", "date", HttpStatus.SC_OK);
    }

    @Test
    public void testInvalidDateFormat() {
        executeUserTest("errors[0].description", "29-02-2020", "Date of birth is invalid, should follow the format YYYY-MM-DD", "date", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testEmptyDateFormat() {
        executeUserTest("errors[0].description", "", "Date of birth is invalid, should follow the format YYYY-MM-DD", "date", HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testInvalidStringDateFormat() {
        executeUserTest("errors[0].description", "Jun 30, 2022", "Date of birth is invalid, should follow the format YYYY-MM-DD", "date", HttpStatus.SC_BAD_REQUEST);
    }

    private void executeUserTest(String responsePath, String inputValue, String expectedValue, String fieldKey, int expectedStatus) {
        String requestPayload = generateRequestUserPayload(inputValue, fieldKey);

        if (!requestPayload.isEmpty()) {
            sendRequestAndValidate(responsePath, expectedValue, requestPayload, expectedStatus);
        }
    }

    private void executeBrandTest(String responsePath, String typeValue, String teamValue, String expectedValue, String fieldKey, int expectedStatus) {
        String requestPayload = generateRequestBrandPayload(typeValue, teamValue);

        if (!requestPayload.isEmpty()) {
            sendRequestAndValidate(responsePath, expectedValue, requestPayload, expectedStatus);
        }
    }

    private String generateRequestUserPayload(String fieldValue, String fieldKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode requestBody = createUserPayload(objectMapper, fieldValue, fieldKey);
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            System.err.println("Error creating JSON payload: " + e.getMessage());
        }
        return "";
    }

    private String generateRequestBrandPayload(String fieldValue, String fieldKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode requestBody = createBrandPayload(objectMapper, fieldValue, fieldKey);
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            System.err.println("Error creating JSON payload: " + e.getMessage());
        }
        return "";
    }

    private ObjectNode createUserPayload(ObjectMapper mapper, String fieldValue, String fieldKey) {
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.set("user", createUserNode(mapper, fieldValue, fieldKey));
        return rootNode;
    }

    private ObjectNode createUserNode(ObjectMapper mapper, String fieldValue, String fieldKey) {
        ObjectNode userNode = mapper.createObjectNode();
        userNode.set("birth", createDetailsNode(mapper, fieldValue, fieldKey));
        userNode.set("identification", createIdentificationNode(mapper));
        return userNode;
    }

    private ObjectNode createDetailsNode(ObjectMapper mapper, String fieldValue, String fieldKey) {
        ObjectNode detailsNode = mapper.createObjectNode();
        detailsNode.put(fieldKey, fieldValue);
        return detailsNode;
    }

    private ObjectNode createIdentificationNode(ObjectMapper mapper) {
        ObjectNode idNode = mapper.createObjectNode();
        idNode.put("number", "1234567890");
        idNode.put("type", "national_id");
        idNode.put("issuer", "US");
        return idNode;
    }

    private ObjectNode createBrandPayload(ObjectMapper mapper, String typeValue, String teamValue) {
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.set("brand", createBrandNode(mapper, typeValue, teamValue));
        return rootNode;
    }

    private ObjectNode createBrandNode(ObjectMapper mapper, String typeValue, String teamValue) {
        ObjectNode brandNode = mapper.createObjectNode();
        brandNode.set("name", createBrandNameNode(mapper));
        brandNode.set("segment", createSegmentNode(mapper, typeValue, teamValue));
        return brandNode;
    }

    private ObjectNode createBrandNameNode(ObjectMapper mapper) {
        ObjectNode nameNode = mapper.createObjectNode();
        nameNode.put("en", "brand name Test");
        return nameNode;
    }

    private ObjectNode createSegmentNode(ObjectMapper mapper, String typeValue, String teamValue) {
        ObjectNode segmentNode = mapper.createObjectNode();
        segmentNode.set("type", createSegmentTypeNode(mapper, typeValue));
        segmentNode.set("team", createTeamNode(mapper, teamValue));
        return segmentNode;
    }

    private ObjectNode createSegmentTypeNode(ObjectMapper mapper, String value) {
        ObjectNode typeNode = mapper.createObjectNode();
        typeNode.put("code", value);
        return typeNode;
    }

    private ObjectNode createTeamNode(ObjectMapper mapper, String value) {
        ObjectNode teamNode = mapper.createObjectNode();
        teamNode.put("code", value);
        return teamNode;
    }

    private void sendRequestAndValidate(String responsePath, String expectedValue, String requestBody, int expectedStatus) {
        RequestSpecification requestSpec = given()
                .header(HEADER_CONTENT_TYPE, APPLICATION_JSON)
                .header(HEADER_AUTH, BEARER_TOKEN);

        requestSpec.body(requestBody)
                .when()
                .log().all()
                .post()
                .then()
                .statusCode(expectedStatus)
                .body(responsePath, equalTo(expectedValue));
    }
}
