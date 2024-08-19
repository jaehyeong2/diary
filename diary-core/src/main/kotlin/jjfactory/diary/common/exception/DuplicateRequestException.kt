package jjfactory.diary.common.exception

import jjfactory.diary.common.exception.BizBaseException
import jjfactory.diary.common.exception.ErrorCode

class DuplicateRequestException: BizBaseException(ErrorCode.CONFLICT_DUPLICATE_REQUEST) {

}