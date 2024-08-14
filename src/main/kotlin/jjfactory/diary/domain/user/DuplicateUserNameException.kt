package jjfactory.diary.domain.user

import jjfactory.diary.common.exception.BizBaseException
import jjfactory.diary.common.exception.ErrorCode

class DuplicateUserNameException: BizBaseException(ErrorCode.CONFLICT_DUPLICATE_USERNAME) {

}