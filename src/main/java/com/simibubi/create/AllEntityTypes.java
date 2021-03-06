package com.simibubi.create;

import com.simibubi.create.content.contraptions.components.structureMovement.ContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.ContraptionEntityRenderer;
import com.simibubi.create.content.contraptions.components.structureMovement.glue.SuperGlueEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.glue.SuperGlueRenderer;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class AllEntityTypes {

	public static final RegistryEntry<EntityType<ContraptionEntity>> CONTRAPTION =
			register("contraption", ContraptionEntity::new, EntityClassification.MISC, 5, 3, true, ContraptionEntity::build);
	public static final RegistryEntry<EntityType<ContraptionEntity>> STATIONARY_CONTRAPTION =
			register("stationary_contraption", ContraptionEntity::new, EntityClassification.MISC, 20, 40, false, ContraptionEntity::build);
	public static final RegistryEntry<EntityType<SuperGlueEntity>> SUPER_GLUE =
			register("super_glue", SuperGlueEntity::new, EntityClassification.MISC, 10, Integer.MAX_VALUE, false, SuperGlueEntity::build);

	private static <T extends Entity> RegistryEntry<EntityType<T>> register(String name, IFactory<T> factory,
			EntityClassification group, int range, int updateFrequency, boolean sendVelocity,
			NonNullConsumer<EntityType.Builder<T>> propertyBuilder) {
		String id = Lang.asId(name);
		return Create.registrate().entity(id, factory, group)
				.properties(b -> b
					.setTrackingRange(range)
					.setUpdateInterval(updateFrequency)
					.setShouldReceiveVelocityUpdates(sendVelocity))
				.properties(propertyBuilder)
				.register();
	}
	
	public static void register() {}

	@OnlyIn(value = Dist.CLIENT)
	public static void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(STATIONARY_CONTRAPTION.get(), ContraptionEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CONTRAPTION.get(), ContraptionEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(SUPER_GLUE.get(), SuperGlueRenderer::new);
	}
}
