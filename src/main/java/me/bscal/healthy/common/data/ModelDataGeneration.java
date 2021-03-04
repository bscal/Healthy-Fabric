package me.bscal.healthy.common.data;

import net.minecraft.util.Identifier;

public final class ModelDataGeneration
{

	public static final String GENERATED = "generated";
	public static final String HANDHELD = "handheld";
	public static final String BLOCK = "block";

	public static String createItemModelJson(Identifier id, String type)
	{
		if (GENERATED.equals(type) || HANDHELD.equals(type))
		{
			//The two types of items. "handheld" is used mostly for tools and the like, while "generated" is used for everything else.
			return "{\n" +
					"  \"parent\": \"item/" + type + "\",\n" +
					"  \"textures\": {\n" +
					"    \"layer0\": \"" + id.getNamespace() + ":" + id.getPath() + "\"\n" +
					"  }\n" +
					"}";
		}
		else if (BLOCK.equals(type))
		{
			//However, if the item is a block-item, it will have a different model json than the previous two.
			return "{\n" + "  \"parent\": \"" + id.getNamespace() + ":" + id.getPath() + "\"\n" + "}";
		}
		else {
			//If the type is invalid, return an empty json string.
			return "";
		}
	}

}
