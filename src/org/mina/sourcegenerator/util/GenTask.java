package org.mina.sourcegenerator.util;

import org.mina.sourcegenerator.entity.Param;

public interface GenTask {
	public Object generate(Param p) throws Exception;
}
