package com.github.ethanicuss.astraladditions.entities.cogfly;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class CogflyEntityModel<T extends Entity> extends EntityModel<T> {
	private final ModelPart torso;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	public CogflyEntityModel(ModelPart root) {
		this.torso = root.getChild("torso");
		this.left_wing = root.getChild("left_wing");
		this.right_wing = root.getChild("right_wing");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData torso = modelPartData.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -1.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData left_wing = modelPartData.addChild("left_wing", ModelPartBuilder.create().uv(0, 6).cuboid(1.0F, -3.0F, -1.0F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData right_wing = modelPartData.addChild("right_wing", ModelPartBuilder.create().uv(-3, 6).cuboid(-5.0F, -3.0F, -1.0F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 12, 12);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		torso.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left_wing.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		right_wing.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}