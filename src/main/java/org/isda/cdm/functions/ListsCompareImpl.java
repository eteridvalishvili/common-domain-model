package org.isda.cdm.functions;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

import cdm.base.maths.CompareOp;
import cdm.base.maths.functions.ListsCompare;

public class ListsCompareImpl extends ListsCompare {

	@Override
	protected Boolean doEvaluate(CompareOp compareOp, List<BigDecimal> left, List<BigDecimal> right, BigDecimal rightNumber) {
		if (left == null)
			return false;
		if (rightNumber != null) {
			return left.stream().allMatch(leftNumber -> operation(compareOp).apply(leftNumber, rightNumber));
		}
		if (right != null) {
			for (int i = 0; i < left.size(); i++) {
				if (i < right.size()) {
					if (!operation(compareOp).apply(left.get(i), right.get(i))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private BiFunction<BigDecimal, BigDecimal, Boolean> operation(CompareOp compareOp) {
		BiFunction<BigDecimal, BigDecimal, Boolean> op = null;
		switch (compareOp) {
		case GREATER:
			op = (b1, b2) -> b1.compareTo(b2) > 0;
			break;
		case EQUAL:
			op = (b1, b2) -> b1.compareTo(b2) == 0;
			break;
		default:
			break;
		}
		return op;
	}
}
