package cn.caishen.worklog.service.analysis;

import cn.caishen.domain.domain.po.WorkHour;
import cn.caishen.domain.domain.vo.WorkHourDetailVo;
import cn.caishen.domain.utils.LbMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AnalysisService {

    PageInfo<LbMap> workHourList(int page, int limit, LbMap param);

    PageInfo<WorkHourDetailVo> workDetailList(int page, int limit, LbMap param);

    /**
     * 查询开发总数及开发完成数
     * @return
     */
    LbMap queryDevCount(LbMap param);

    /**
     * 查询测试总数及测试完成数
     * @param param
     * @return
     */
    LbMap queryTestCount(LbMap param);

    /**
     * 查询所有总数及完成数
     * @param param
     * @return
     */
    LbMap queryAllCount(LbMap param);

    /**
     * 查询所有客反数据，今年，去年，前年
     * @param param
     * @return
     * @throws Exception
     */
    LbMap queryFeedbackList(LbMap param) throws Exception;

    /**
     * 查询内部BUG及需求，客户BUG及需求
     * @param param
     * @return
     */
    LbMap queryFeedbackTypeList(LbMap param);

    /**
     * 查询优先级
     * @param param
     * @return
     */
    LbMap queryPriority(LbMap param);

    /**
     * 查询各版本反馈列表
     * @param param
     * @return
     */
    List<LbMap> queryVersionList(LbMap param);

    LbMap workHourDoJob() throws Exception;

    /**
     * PDF导出工时统计
     * @param param
     * @return
     */
    List<WorkHour> queryWorkHourList(LbMap param);

    /**
     * PDF导出工时统计明细
     * @param param
     * @return
     */
    List<WorkHourDetailVo> queryWorkHourDetailList(LbMap param);
}
