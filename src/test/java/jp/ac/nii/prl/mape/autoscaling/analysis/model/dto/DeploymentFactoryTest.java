package jp.ac.nii.prl.mape.autoscaling.analysis.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

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
	public void instanceTypeToDTO() {
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
	public void instanceTypeDTOToInstanceType() {
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

}
