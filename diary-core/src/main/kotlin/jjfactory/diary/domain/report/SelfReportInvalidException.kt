package jjfactory.diary.domain.report

import jjfactory.diary.common.exception.BizBaseException
import jjfactory.diary.common.exception.ErrorCode

class SelfReportInvalidException: BizBaseException(ErrorCode.CONFLICT_SELF_REPORT_REQUEST) {

}