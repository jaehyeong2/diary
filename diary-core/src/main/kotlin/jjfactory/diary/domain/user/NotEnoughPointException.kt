package jjfactory.diary.domain.user

import jjfactory.diary.common.exception.BizBaseException
import jjfactory.diary.common.exception.ErrorCode

class NotEnoughPointException: BizBaseException(ErrorCode.CONFLICT_NOT_ENOUGH_POINT) {

}