package jp.ac.nii.prl.mape.autoscaling.analysis.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import jp.ac.nii.prl.mape.autoscaling.analysis.MapeAutoscalingAnalysisApplication;
import jp.ac.nii.prl.mape.autoscaling.analysis.TestContext;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Deployment;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.VirtualMachine;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.DeploymentService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestContext.class, MapeAutoscalingAnalysisApplication.class})
@WebAppConfiguration
public class DeploymentControllerTest {

	private MockMvc mockMvc;
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private DeploymentService deploymentService;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
	
	@Before
	public void init() {
		Mockito.reset(deploymentService);
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testCreateDeployment() throws Exception {
		Deployment deployment = new Deployment();
		deployment.setVms(new ArrayList<VirtualMachine>());
		String content = json(deployment);
		
		mockMvc.perform(post("/deployment")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(content))
			.andExpect(status().isCreated());
	}
	
	@Test
	public void testFindAllDeploymentsEmptyDeployments() throws Exception {
		
		Deployment first = new Deployment();
		first.setId(1);
		first.setVms(new ArrayList<VirtualMachine>());
		System.out.println(first.toString());
		Deployment second = new Deployment();
		second.setId(2);
		second.setVms(new ArrayList<VirtualMachine>());
		System.out.println(second.toString());
		when(deploymentService.findAll()).thenReturn(Arrays.asList(first, second));
		mockMvc.perform(get("/deployment"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].vms", hasSize(0)));

		verify(deploymentService, times(1)).findAll();
		verifyNoMoreInteractions(deploymentService);
	}
	
	@Test
	public void testFindDeploymentHttpStatusCode404() throws Exception {
		when(deploymentService.findById(1)).thenThrow(new DeploymentNotFoundException(""));
		
		mockMvc.perform(get("/deployment/{id}", 1))
			.andExpect(status().isNotFound());
		
		verify(deploymentService, times(1)).findById(1);
		verifyNoMoreInteractions(deploymentService);
	}
	
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
