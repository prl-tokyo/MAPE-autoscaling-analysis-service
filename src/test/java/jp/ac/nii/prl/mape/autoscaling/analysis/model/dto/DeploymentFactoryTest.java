package jp.ac.nii.prl.mape.autoscaling.analysis.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import jp.ac.nii.prl.mape.autoscaling.analysis.model.Adaptation;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Deployment;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.Instance;
import jp.ac.nii.prl.mape.autoscaling.analysis.model.InstanceType;

@RunWith(MockitoJUnitRunner.class)
public class DeploymentFactoryTest {

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testDeploymentCreation() {
		Assert.assertTrue(true);
	}
	
	@Test
	public void testInstanceTypeToDTO() {
		
		Deployment deployment = new Deployment();
		
		InstanceType instanceType = new InstanceType();
		instanceType.setId(1);
		instanceType.setDeployment(deployment);
		instanceType.setInstances(new ArrayList<Instance>());
		instanceType.setTypeCost(1.0d);
		instanceType.setTypeCPUs(1);
		instanceType.setTypeID("t2.micro");
		instanceType.setTypeRAM(1.0d);
		
		InstanceTypeDTO dto = DeploymentFactory.createInstanceTypeDTO(instanceType);
		
		Assert.assertEquals(dto.getTypeID(), instanceType.getTypeID());
		Assert.assertEquals(dto.getTypeCost(), instanceType.getTypeCost());
		Assert.assertEquals(dto.getTypeCPUs(), instanceType.getTypeCPUs());
		Assert.assertEquals(dto.getTypeRAM(), instanceType.getTypeRAM());
		
	}
	
	@Test
	public void testInstanceTypeDTOToInstanceType() {
		
		InstanceTypeDTO dto = new InstanceTypeDTO();
		
		dto.setTypeCost(1.0d);
		dto.setTypeCPUs(2);
		dto.setTypeID("t2.large");
		dto.setTypeRAM(2.0d);
		
		Deployment deployment = new Deployment();
		List<Instance> instances = new ArrayList<>();
		
		InstanceType instanceType = DeploymentFactory.createInstanceType(dto, deployment, instances);
		
		Assert.assertEquals(dto.getTypeID(), instanceType.getTypeID());
		Assert.assertEquals(dto.getTypeCost(), instanceType.getTypeCost());
		Assert.assertEquals(dto.getTypeCPUs(), instanceType.getTypeCPUs());
		Assert.assertEquals(dto.getTypeRAM(), instanceType.getTypeRAM());
		Assert.assertEquals(deployment, instanceType.getDeployment());
		Assert.assertEquals(instances, instanceType.getInstances());
		
	}
	
	@Test
	public void testInstanceToDTO() {
		
		Deployment deployment = new Deployment();
		InstanceType instanceType = new InstanceType();
		
		Instance instance = new Instance();
		
		instance.setDeployment(deployment);
		instance.setId(1);
		instance.setInstanceType(instanceType);
		instance.setInstID("i-68def98");
		instance.setInstLoad(0.06d);
		instance.setInstType("t2.micro");
		
		InstanceDTO dto = DeploymentFactory.createInstanceDTO(instance);
		
		Assert.assertEquals(instance.getInstID(), dto.getInstID());
		Assert.assertEquals(instance.getInstLoad(), dto.getInstLoad());
		Assert.assertEquals(instance.getInstType(), dto.getInstType());
		
	}
	
	@Test
	public void testInstanceDTOToInstance() {
		
		InstanceDTO dto = new InstanceDTO();
		
		dto.setInstID("i-68def98");
		dto.setInstLoad(0.55d);
		dto.setInstType("t2.large");
		
		Deployment deployment = new Deployment();
		InstanceType instanceType = new InstanceType();
		
		Instance instance = DeploymentFactory.createInstance(dto, deployment, instanceType);
		
		Assert.assertEquals(dto.getInstID(), instance.getInstID());
		Assert.assertEquals(dto.getInstType(), instance.getInstType());
		Assert.assertEquals(dto.getInstLoad(), instance.getInstLoad());
		Assert.assertEquals(deployment, instance.getDeployment());
		Assert.assertEquals(instanceType, instance.getInstanceType());
		
	}
	
	@Test
	public void testAdaptationToDTO() {
		
		Deployment deployment = new Deployment();
		Adaptation adaptation = new Adaptation();
		
		adaptation.setAdapt(true);
		adaptation.setCpuCount(2);
		adaptation.setDeployment(deployment);
		adaptation.setId(1);
		adaptation.setScaleUp(true);
		
		AdaptationDTO dto = DeploymentFactory.createAdaptationDTO(adaptation);
		
		Assert.assertEquals(adaptation.getCpuCount(), dto.getCpuCount());
		Assert.assertEquals(adaptation.isAdapt(), dto.isAdapt());
		Assert.assertEquals(adaptation.isScaleUp(), dto.isScaleUp());
		
	}
	
	@Test
	public void testAdaptationDTOToAdaptation() {
		
		Deployment deployment = new Deployment();
		AdaptationDTO dto = new AdaptationDTO();
		
		dto.setAdapt(true);
		dto.setCpuCount(4);
		dto.setScaleUp(false);
		
		Adaptation adaptation = DeploymentFactory.createAdaptation(dto, deployment);
		
		Assert.assertEquals(dto.isAdapt(), adaptation.isAdapt());
		Assert.assertEquals(dto.getCpuCount(), adaptation.getCpuCount());
		Assert.assertEquals(dto.isScaleUp(), adaptation.isScaleUp());
		Assert.assertEquals(deployment, adaptation.getDeployment());
		
	}

	@Test
	public void testDeploymentToDTO() {
		Assert.assertTrue(false);
	}
	
	@Test
	public void testDeploymentDTOToDeployment() {
		Assert.assertTrue(false);
	}
}
