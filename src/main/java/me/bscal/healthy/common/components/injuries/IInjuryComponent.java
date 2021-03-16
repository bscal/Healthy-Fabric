package me.bscal.healthy.common.components.injuries;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface IInjuryComponent extends ComponentV3
{

	Map<Identifier, IInjury> GetInjuries();

	void UpdateInjuries();

	IInjury GetInjury(IInjury injury);

	/**
	 * @param trigger - Should the add trigger a onInjuryStarted event
	 */
	void AddInjury(IInjury injury, boolean trigger);

	/**
	 * @param trigger  - Should the remove trigger a onInjuryRemoved event
	 */
	void RemoveInjury(IInjury injury, boolean trigger);

	boolean HasInjury(IInjury injury);

}
