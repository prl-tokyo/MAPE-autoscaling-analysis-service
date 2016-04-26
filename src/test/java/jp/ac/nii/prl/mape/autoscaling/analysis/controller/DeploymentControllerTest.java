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
import java.util.List;
import java.util.Optional;

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
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Deployment;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.AdaptationService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.DeploymentService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.InstanceService;
import jp.ac.nii.prl.mape.autoscaling.analysis.service.InstanceTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestContext.class, MapeAutoscalingAnalysisApplication.class})
@WebAppConfiguration
public class DeploymentControllerTest {

	private MockMvc mockMvc;
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private DeploymentService deploymentService;
	
	@Autowired
	private InstanceService instanceService;
	
	@Autowired
	private InstanceTypeService instanceTypeService;
	
	@Autowired
	private AdaptationService adaptationService;
	
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
		Mockito.reset(instanceService);
		Mockito.reset(instanceTypeService);
		Mockito.reset(adaptationService);
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testCreateDeployment() throws Exception {
		Deployment deployment = new Deployment();
		deployment.setInstances(new ArrayList<Instance>());
		deployment.setInstanceTypes(new ArrayList<InstanceType>());
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
		first.setInstances(new ArrayList<Instance>());

		Deployment second = new Deployment();
		second.setId(2);
		second.setInstances(new ArrayList<Instance>());

		when(deploymentService.findAll()).thenReturn(Arrays.asList(first, second));
		mockMvc.perform(get("/deployment"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].instances", hasSize(0)))
			.andExpect(jsonPath("$[1].instances", hasSize(0)));

		verify(deploymentService, times(1)).findAll();
		verifyNoMoreInteractions(deploymentService);
	}
	
	@Test
	public void testFindAllDeploymentsWithVMs() throws Exception {
		
		Deployment first = new Deployment();
		first.setId(1);
		List<Instance> vms = new ArrayList<>();
		Instance vm1 = new Instance();
		vm1.setInstID("1");
		vm1.setInstLoad(3.55);
		vm1.setDeployment(first);
		vms.add(vm1);
		Instance vm2 = new Instance();
		vm2.setInstID("1");
		vm2.setInstLoad(1.55);
		vm2.setDeployment(first);
		vms.add(vm2);
		first.setInstances(vms);

		Deployment second = new Deployment();
		second.setId(2);
		second.setInstances(new ArrayList<Instance>());
		second.setInstanceTypes(new ArrayList<InstanceType>());

		when(deploymentService.findAll()).thenReturn(Arrays.asList(first, second));
		mockMvc.perform(get("/deployment"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].instances", hasSize(2)))
			.andExpect(jsonPath("$[1].instances", hasSize(0)));

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
	
	@Test
	public void testFindDeploymentFound() throws Exception {
		Deployment deployment = new Deployment();
		deployment.setId(1);
		deployment.setInstances(new ArrayList<Instance>());
		
		when(deploymentService.findById(1)).thenReturn(Optional.of(deployment));
		
		mockMvc.perform(get("/deployment/{deploymentId}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.instances", hasSize(0)));
		
		verify(deploymentService, times(1)).findById(1);
		verify(deploymentService, times(1)).analyse(deployment);
		verifyNoMoreInteractions(deploymentService);
	}
	
	@Test
	public void testFindVMsInDeployment() throws Exception {
		Deployment first = new Deployment();
		first.setId(1);
		List<Instance> vms = new ArrayList<>();
		Instance vm1 = new Instance();
		vm1.setInstID("1");
		vm1.setInstLoad(3.55);
		vm1.setInstType("t2.micro");
		vm1.setDeployment(first);
		vms.add(vm1);
		Instance vm2 = new Instance();
		vm2.setInstID("2");
		vm2.setInstLoad(1.55);
		vm2.setInstType("t2.micro");
		vm2.setDeployment(first);
		vms.add(vm2);
		List<InstanceType> instanceTypes = new ArrayList<>();
		InstanceType it1 = new InstanceType();
		it1.setId(1);
		it1.setTypeCost(0.01d);
		it1.setTypeCPUs(1);
		it1.setTypeRAM(1d);
		it1.setTypeID("t2.micro");
		instanceTypes.add(it1);
		first.setInstances(vms);
		first.setInstanceTypes(instanceTypes);
		when(instanceService.findByDeploymentId(1)).thenReturn(vms);
		
		mockMvc.perform(get("/deployment/{deploymentId}/instances", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].instID", is("1")))
			.andExpect(jsonPath("$[0].instLoad", is(3.55)))
			.andExpect(jsonPath("$[1].instID", is("2")))
			.andExpect(jsonPath("$[1].instLoad", is(1.55)));
		
		verify(instanceService, times(1)).findByDeploymentId(1);
		verifyNoMoreInteractions(instanceService);
	}
	
	@Test
	public void testGetAnalysis() throws Exception {
		Deployment first = new Deployment();
		first.setId(1);
		List<Instance> vms = new ArrayList<>();
		Instance vm1 = new Instance();
		vm1.setInstID("1");
		vm1.setInstLoad(3.55);
		vm1.setDeployment(first);
		vms.add(vm1);
		Instance vm2 = new Instance();
		vm2.setInstID("2");
		vm2.setInstLoad(1.55);
		vm2.setDeployment(first);
		vms.add(vm2);
		first.setInstances(vms);
		
		Adaptation adaptation = new Adaptation();
		adaptation.setId(1);
		adaptation.setAdapt(true);
		adaptation.setCpuCount(2);
		adaptation.setScaleUp(true);
		adaptation.setDeployment(first);
		
		when(adaptationService.findByDeploymentId(1)).thenReturn(Optional.of(adaptation));
		when(deploymentService.findById(1)).thenReturn(Optional.of(first));
		
		mockMvc.perform(get("/deployment/{deploymentId}/analysis", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.adapt", is(true)))
			.andExpect(jsonPath("$.scaleUp", is(true)))
			.andExpect(jsonPath("$.cpuCount", is(2)));
		
		verify(adaptationService, times(1)).findByDeploymentId(1);
//		verify(deploymentService, times(1)).findById(1);
		verifyNoMoreInteractions(deploymentService);
	}
	
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
