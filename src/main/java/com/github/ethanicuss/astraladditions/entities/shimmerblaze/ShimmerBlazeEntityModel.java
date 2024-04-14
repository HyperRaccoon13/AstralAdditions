package com.github.ethanicuss.astraladditions.entities.shimmerblaze;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;
import java.util.Objects;

@Environment(value= EnvType.CLIENT)
public class ShimmerBlazeEntityModel<T extends Entity>
        extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart[] rods;
    private final ModelPart head;

    public ShimmerBlazeEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.rods = new ModelPart[12];
        Arrays.setAll(this.rods, index -> root.getChild(ShimmerBlazeEntityModel.getRodName(index)));
    }

    private static String getRodName(int index) {
        return "part" + index;
    }

    public static TexturedModelData getTexturedModelData() {
        float j;
        float h;
        float g;
        int i;
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f), ModelTransform.NONE);
        float f = 0.0f;
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 16).cuboid(0.0f, 0.0f, 0.0f, 2.0f, 8.0f, 2.0f);
        for (i = 0; i < 4; ++i) {
            g = MathHelper.cos(f) * 9.0f;
            h = -2.0f + MathHelper.cos((float)(i * 2) * 0.25f);
            j = MathHelper.sin(f) * 9.0f;
            modelPartData.addChild(ShimmerBlazeEntityModel.getRodName(i), modelPartBuilder, ModelTransform.pivot(g, h, j));
            f += 1.5707964f;
        }
        f = 0.7853982f;
        for (i = 4; i < 8; ++i) {
            g = MathHelper.cos(f) * 7.0f;
            h = 2.0f + MathHelper.cos((float)(i * 2) * 0.25f);
            j = MathHelper.sin(f) * 7.0f;
            modelPartData.addChild(ShimmerBlazeEntityModel.getRodName(i), modelPartBuilder, ModelTransform.pivot(g, h, j));
            f += 1.5707964f;
        }
        f = 0.47123894f;
        for (i = 8; i < 12; ++i) {
            g = MathHelper.cos(f) * 5.0f;
            h = 11.0f + MathHelper.cos((float)i * 1.5f * 0.5f);
            j = MathHelper.sin(f) * 5.0f;
            modelPartData.addChild(ShimmerBlazeEntityModel.getRodName(i), modelPartBuilder, ModelTransform.pivot(g, h, j));
            f += 1.5707964f;
        }
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        int i;
        float f = animationProgress * (float)Math.PI * -0.1f;
        boolean d = false;
        if (entity instanceof ShimmerBlazeEntity){
            if (Objects.equals(((ShimmerBlazeEntity) entity).getAttack(), "chase")){
                d = true;
                animationProgress *= 2;
            }
        }
        //d = true;
        //animationProgress *= 2;
        for (i = 0; i < 4; ++i) {
            this.rods[i].pivotX = MathHelper.cos(f) * 9.0f;
            this.rods[i].pivotZ = MathHelper.sin(f) * 9.0f;
            if (d) {
                this.rods[i].pivotY = 5.0f + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25f);
                this.rods[i].yaw = -f + 1.0f;
                this.rods[i].pitch = 1.2f;
            }
            else{
                this.rods[i].pivotY = -2.0f + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25f);
                this.rods[i].yaw = f/3;
                this.rods[i].pitch = 0.12f;
            }
            f += 1.5707964f;
        }
        f = 0.7853982f + animationProgress * (float)Math.PI * 0.03f;
        for (i = 4; i < 8; ++i) {
            this.rods[i].pivotX = MathHelper.cos(f) * 7.0f;
            this.rods[i].pivotZ = MathHelper.sin(f) * 7.0f;
            if (d) {
                this.rods[i].pivotY = 8.0f + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25f);
                this.rods[i].yaw = -f + 2.2f;
                this.rods[i].pitch = 1.2f;
            }
            else{
                this.rods[i].pivotY = 2.0f + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25f);
                this.rods[i].yaw = f/3;
                this.rods[i].pitch = 0.12f;
            }
            f += 1.5707964f;
        }
        f = 0.47123894f + animationProgress * (float)Math.PI * -0.05f;
        for (i = 8; i < 12; ++i) {
            this.rods[i].pivotX = MathHelper.cos(f) * 5.0f;
            this.rods[i].pivotZ = MathHelper.sin(f) * 5.0f;
            if (d) {
                this.rods[i].pivotY = 12.0f + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25f);
                this.rods[i].yaw = -f + 1.0f;
                this.rods[i].pitch = 1.2f;
            }
            else{
                this.rods[i].pivotY = 11.0f + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25f);
                this.rods[i].yaw = f/3;
                this.rods[i].pitch = 0.12f;
            }
            f += 1.5707964f;
        }
        this.head.yaw = headYaw * ((float)Math.PI / 180);
        this.head.pitch = headPitch * ((float)Math.PI / 180);
    }
}