package com.berryman.customer.api;

import com.berryman.customer.model.Customer;
import com.berryman.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTest {

    private static final Integer CUSTOMER_ID = 1;
    private static final String CUSTOMER_NAME = "Banana";
    private static final String CUSTOMER_SURNAME = "Bananaman";

    private MockMvc mockMvc;

    private CustomerService service;

    private Controller controller;

    private static final Customer stubCustomer = new Customer();

    static {
        stubCustomer.setId(CUSTOMER_ID);
        stubCustomer.setName(CUSTOMER_NAME);
        stubCustomer.setSurname(CUSTOMER_SURNAME);
    }

    @Before
    public void init() {
        service = mock(CustomerService.class);
        when(service.addCustomer(any(Customer.class))).thenReturn(stubCustomer);
        when(service.findCustomerById(CUSTOMER_ID)).thenReturn(stubCustomer);
        controller = new Controller(service);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    @SneakyThrows
    public void createCustomerShouldEchoNewlyCreatedCustomer() {
        mockMvc.perform(post("/customers/create")
                .contentType(APPLICATION_JSON)
                .content(testCustomerAsJson(stubCustomer)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(CUSTOMER_ID)))
                .andExpect(jsonPath("$.name", is(CUSTOMER_NAME)))
                .andExpect(jsonPath("$.surname", is(CUSTOMER_SURNAME)));
    }

    @Test
    @SneakyThrows
    public void getCustomerShouldReturnCustomerWithGivenId() {
        mockMvc.perform(get("/customers/find/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(CUSTOMER_ID)))
                .andExpect(jsonPath("$.name", is(CUSTOMER_NAME)))
                .andExpect(jsonPath("$.surname", is(CUSTOMER_SURNAME)));
    }

    @SneakyThrows
    private String testCustomerAsJson(Customer customer) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(customer);
    }

}
