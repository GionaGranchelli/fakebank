package com.fakebank.assignment.integration;

import com.fakebank.assignment.config.WebConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static com.fakebank.assignment.config.UtilsTest.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties="xml-schema=./src/main/resources/xml/schema-records.xsd")
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class IntegrationTestController {

    private final String EXPECTED_RESPONSE_FROM_CSV = "[{\"transactionId\":\"112806\",\"transactionDescription\":\"Candy for Willem Theuï¿½\",\"failureDescription\":\"TRANSACTION_NOT_UNIQUE\"},{\"transactionId\":\"112806\",\"transactionDescription\":\"Flowers from Erik de Vries\",\"failureDescription\":\"TRANSACTION_NOT_UNIQUE\"}]";
    private final String EXPECTED_RESPONSE_FROM_XML = "[{\"transactionId\":\"189177\",\"transactionDescription\":\"Subscription for Erik Dekker\",\"failureDescription\":\"BALANCE_MISMATCH\"},{\"transactionId\":\"163215\",\"transactionDescription\":\"Subscription for Erik TheuÃ\u009F\",\"failureDescription\":\"BALANCE_MISMATCH\"}]";
    private final String EXPECTED_RESPONSE_FROM_WRONG_XML = "{\"code\":400,\"errorDescription\":\"Xml File not valid, caused by: cvc-elt.1: Cannot find the declaration of element 'note'.\"}";
    private final String EXPECTED_RESPONSE_FROM_WRONG_CSV = "{\"code\":400,\"errorDescription\":\"Header of CSV File not compatible with default format. Header must be :  Reference,Account Number,Description,Start Balance,Mutation,End Balance\"}";
    private final String EXPECTED_RESPONSE_FROM_WRONG_MIMETYPE = "{\"code\":415,\"errorDescription\":\"File media type: text/plain, is not supported\"}";
    private final String EXPECTED_RESPONSE_FROM_EMPTY_FILE = "{\"code\":400,\"errorDescription\":\"File is Empty\"}";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void uploadOfGoodCsvIsSuccessful() throws Exception {
        MockMultipartFile file = getCSVMockMultipartFile();

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/loadStatement").file(file))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].transactionId", is("112806")))
                .andExpect(jsonPath("$[0].transactionDescription", is("Candy for Willem Theu�")))
                .andExpect(jsonPath("$[0].failureDescription", is("TRANSACTION_NOT_UNIQUE")))
                .andExpect(jsonPath("$[1].transactionId", is("112806")))
                .andExpect(jsonPath("$[1].transactionDescription", is("Flowers from Erik de Vries")))
                .andExpect(jsonPath("$[1].failureDescription", is("TRANSACTION_NOT_UNIQUE")))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(EXPECTED_RESPONSE_FROM_CSV, contentAsString );
    }

    @Test
    public void uploadOfGoodXmlIsSuccessful() throws Exception {
        MockMultipartFile file = getXMLMockMultipartFile();

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/loadStatement").file(file))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].transactionId", is("189177")))
                .andExpect(jsonPath("$[0].transactionDescription", is("Subscription for Erik Dekker")))
                .andExpect(jsonPath("$[0].failureDescription", is("BALANCE_MISMATCH")))
                .andExpect(jsonPath("$[1].transactionId", is("163215")))
                .andExpect(jsonPath("$[1].transactionDescription", is("Subscription for Erik Theuß")))
                .andExpect(jsonPath("$[1].failureDescription", is("BALANCE_MISMATCH")))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(EXPECTED_RESPONSE_FROM_XML, contentAsString );
    }

    @Test
    public void uploadOfBadSchemaXmlIsNotSuccessful() throws Exception {
        MockMultipartFile file = getWrongSchemaMultipartFile();

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/loadStatement").file(file))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(EXPECTED_RESPONSE_FROM_WRONG_XML, contentAsString );
    }


    @Test
    public void uploadOfEmptyFileIsNotSuccessful() throws Exception {
        MockMultipartFile file = getEmptyMultipartFile();

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/loadStatement").file(file))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(EXPECTED_RESPONSE_FROM_EMPTY_FILE, contentAsString );
    }

    @Test
    public void uploadOfWrongMimetypeIsNotSuccessful() throws Exception {
        MockMultipartFile file = getWrongMimetypeMultipartFile();

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/loadStatement").file(file))
                .andDo(print())
                .andExpect(status().is(415))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(EXPECTED_RESPONSE_FROM_WRONG_MIMETYPE, contentAsString );
    }
    @Test
    public void uploadOfWrongCsvIsNotSuccessful() throws Exception {
        MockMultipartFile file = getWrongCSVMultipartFile();

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart("/loadStatement").file(file))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(EXPECTED_RESPONSE_FROM_WRONG_CSV, contentAsString );
    }
}
