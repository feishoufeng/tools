package cc.zytrip.service;

import cc.zytrip.model.Property;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AnalysisPropertyTest {

    @Test
    public void test() {
        String pStr = "[{\"menuName\":\"烧杯\",\"names\":[\"产地\",\"壁厚\",\"容量\",\"材质\",\"外型\"]},{\"menuName\":\"量筒\",\"names\":[\"产地\",\"容量\",\"材质\",\"台座\",\"精度\",\"是否带塞子\"]},{\"menuName\":\"三角烧瓶/锥形瓶\",\"names\":[\"容量\",\"口径类型\",\"口径大小\",\"产地\"]},{\"menuName\":\"量杯\",\"names\":[\"产地\",\"容量\",\"材质\",\"品牌\",\"是否带把\"]},{\"menuName\":\"容量瓶\",\"names\":[\"瓶塞材质\",\"产地\",\"材质\",\"容量\",\"颜色\",\"精度\",\"瓶塞材质\",\"产地\",\"材质\",\"容量\",\"颜色\",\"精度\"]},{\"menuName\":\"胖肚移液管\",\"names\":[\"产地\",\"材质\",\"量程\",\"精度\"]},{\"menuName\":\"刻度吸管\",\"names\":[\"精度\",\"量程\",\"材质\",\"产地\"]},{\"menuName\":\"试管\",\"names\":[\"直径\",\"类型\",\"材质\",\"长度\",\"是否有刻度\",\"产地\",\"管口特征\",\"管底形状\"]},{\"menuName\":\"搪瓷盘·桶\",\"names\":[\"类型\",\"产地\"]},{\"menuName\":\"不锈钢方盘\",\"names\":[\"产地\",\"类型\",\"材质\",\"长\",\"宽\",\"高\"]},{\"menuName\":\"塑料盒\",\"names\":[\"产地\",\"容量\",\"盒体材质\",\"盖子材质\"]},{\"menuName\":\"不锈钢筐篮\",\"names\":[\"产地\",\"形状\",\"长（mm）\",\"直径（mm）\",\"高（mm）\"]},{\"menuName\":\"铝盒\",\"names\":[\"产地\",\"容量\"]},{\"menuName\":\"塑料托盘\",\"names\":[\"尺寸\",\"颜色\",\"产地\",\"材质\"]},{\"menuName\":\"塑料喷壶\",\"names\":[\"容量\",\"产地\"]},{\"menuName\":\"塑料箱/塑料篮\",\"names\":[\"类型\",\"容积\",\"颜色\",\"产地\",\"类别\"]},{\"menuName\":\"不锈钢药棉缸\",\"names\":[\"直径\",\"产地\"]},{\"menuName\":\"不锈钢瓶/桶\",\"names\":[\"产地\",\"类别\",\"容量\"]},{\"menuName\":\"试管架\",\"names\":[\"类别\",\"孔数\",\"材质\",\"产地\",\"适用试管直径\"]},{\"menuName\":\"试管夹\",\"names\":[\"材质\",\"产地\"]},{\"menuName\":\"存储瓶\",\"names\":[]},{\"menuName\":\"防溅球\",\"names\":[\"产地\",\"接口尺寸\"]},{\"menuName\":\"四氟容器\",\"names\":[\"直径\",\"高度\",\"产地\"]},{\"menuName\":\"移液管架\",\"names\":[\"产地\",\"材质\"]},{\"menuName\":\"试剂瓶\",\"names\":[\"瓶口类型\",\"容量\",\"产地\",\"颜色\",\"类别\"]},{\"menuName\":\"自封袋\",\"names\":[\"厚度\",\"型号\",\"尺寸\",\"产地\"]},{\"menuName\":\"样品瓶及盖垫\",\"names\":[\"颜色\",\"产地\",\"类型\",\"容量\"]},{\"menuName\":\"小口瓶/窄口瓶\",\"names\":[\"产地\",\"外观\",\"容量\",\"瓶体材质\",\"瓶盖材质\"]},{\"menuName\":\"大口瓶/广口瓶\",\"names\":[\"产地\",\"容量\",\"外观\",\"瓶体材质\",\"瓶盖材质\"]},{\"menuName\":\"氟化瓶·氟化桶\",\"names\":[\"口径\",\"产地\",\"类别\",\"是否带UN标识\",\"容量\",\"盖子类型\"]},{\"menuName\":\"种子瓶\",\"names\":[\"容量\",\"产地\"]},{\"menuName\":\"碘瓶\",\"names\":[\"容积\",\"产地\"]},{\"menuName\":\"灭菌袋\",\"names\":[\"产地\",\"宽度\"]},{\"menuName\":\"滴瓶\",\"names\":[\"颜色\",\"容量\",\"产地\",\"瓶体材质\"]},{\"menuName\":\"样品标签\",\"names\":[\"产地\",\"规格\",\"颜色\",\"标签形状\"]},{\"menuName\":\"标记笔\",\"names\":[\"规格\",\"颜色\",\"产地\"]},{\"menuName\":\"样品管\",\"names\":[]},{\"menuName\":\"标识胶带\",\"names\":[\"规格\",\"颜色\",\"产地\"]},{\"menuName\":\"样品管盖\",\"names\":[]},{\"menuName\":\"圆底溶剂安瓿瓶\",\"names\":[\"容量\",\"产地\",\"管外径\"]},{\"menuName\":\"玻璃标本瓶\",\"names\":[\"内高\",\"产地\",\"内直径\"]},{\"menuName\":\"瓶盖\",\"names\":[\"适用胶管内径\",\"产地\",\"规格\"]},{\"menuName\":\"烧瓶\",\"names\":[\"类型\",\"容量\",\"产地\"]},{\"menuName\":\"冷凝管\",\"names\":[\"上下磨口\",\"类别\",\"产地\",\"有效长度\"]},{\"menuName\":\"烧瓶托\",\"names\":[\"材质\",\"外径\",\"产地\"]},{\"menuName\":\"橡胶管\",\"names\":[\"外径\",\"产地\",\"壁厚\",\"内径\"]},{\"menuName\":\"乳胶管\",\"names\":[\"壁厚\",\"产地\",\"内径×外径\"]},{\"menuName\":\"硅胶管\",\"names\":[\"内径\",\"外径\",\"壁厚\",\"产地\"]},{\"menuName\":\"分馏头\",\"names\":[\"上磨口\",\"右下磨口\",\"温度计磨口\",\"左下磨口\",\"产地\"]},{\"menuName\":\"接头\",\"names\":[]},{\"menuName\":\"蒸馏头\",\"names\":[\"产地\",\"上磨口\",\"下磨口\",\"侧磨口\"]},{\"menuName\":\"夹子\",\"names\":[\"类型\",\"产地\"]},{\"menuName\":\"双排管\",\"names\":[]},{\"menuName\":\"三通接头\",\"names\":[\"阀门类型\",\"产地\",\"磨口\"]},{\"menuName\":\"导气管\",\"names\":[\"产地\",\"磨口\"]},{\"menuName\":\"烧杯架\",\"names\":[\"材质\",\"产地\"]},{\"menuName\":\"升降台\",\"names\":[\"最大高度\",\"边长\",\"有无胶板\",\"产地\",\"材质\"]},{\"menuName\":\"铁圈\",\"names\":[\"产地\"]},{\"menuName\":\"反应瓶\",\"names\":[\"类别\",\"产地\",\"阀门直径\",\"阀门类型\",\"容量\",\"磨口\"]},{\"menuName\":\"抽气接头\",\"names\":[\"节门类别\",\"外形\",\"磨口\",\"产地\",\"磨口类别\"]},{\"menuName\":\"反应管\",\"names\":[\"砂板规格\",\"磨口\",\"螺口\",\"节门\",\"产地\",\"容量\"]},{\"menuName\":\"转接头\",\"names\":[\"类别\",\"产地\",\"上下口\"]},{\"menuName\":\"鼓泡器\",\"names\":[\"底部形状\",\"规格\",\"管直径\",\"产地\",\"高度\"]},{\"menuName\":\"接受管\",\"names\":[\"类别\",\"磨口\",\"产地\"]},{\"menuName\":\"巴斯德吸管/一次性吸管\",\"names\":[\"是否消毒\",\"容量\"]},{\"menuName\":\"采样杯\",\"names\":[\"产地\",\"容积\",\"是否带盖\"]},{\"menuName\":\"采样拭子\",\"names\":[]},{\"menuName\":\"毛细管\",\"names\":[\"品类\",\"内径\",\"管长\",\"材质\",\"产地\"]},{\"menuName\":\"铝称量盘/铝盘\",\"names\":[\"表面处理\",\"产地\",\"颜色\",\"形状\",\"容积\",\"描述\"]},{\"menuName\":\"洗耳球·大容量手动移液器\",\"names\":[\"产地\",\"容量\",\"颜色\",\"类型\"]},{\"menuName\":\"药刮\",\"names\":[\"产地\",\"长度\",\"杆直径\"]},{\"menuName\":\"药勺\",\"names\":[\"杆直径\",\"材质\",\"尺寸\",\"类型\",\"产地\"]},{\"menuName\":\"胶头\",\"names\":[\"适用容量\"]},{\"menuName\":\"称量纸\",\"names\":[\"尺寸\",\"产地\"]},{\"menuName\":\"称量瓶\",\"names\":[\"类型\",\"直径（mm）\",\"高度（mm）\",\"产地\"]},{\"menuName\":\"采样袋\",\"names\":[\"材质\",\"容量\",\"阀门数\",\"产地\",\"应用\"]},{\"menuName\":\"不锈钢铲子\",\"names\":[\"大小\",\"口型\",\"产地\",\"材质\"]},{\"menuName\":\"镊子\",\"names\":[]},{\"menuName\":\"胶头滴管\",\"names\":[\"有无缓冲球\",\"类型\"]},{\"menuName\":\"塑料称量盘\",\"names\":[\"产地\",\"是否防静电\",\"容量\",\"外形\"]},{\"menuName\":\"药物天平\",\"names\":[\"量程\",\"分度值\",\"产地\",\"读数方式\"]},{\"menuName\":\"移液器\",\"names\":[\"产地\",\"类型\",\"最大量程\",\"通道\",\"灭菌方式\"]},{\"menuName\":\"吸头\",\"names\":[\"是否灭菌\",\"包装方式\",\"量程范围\",\"有无滤芯\",\"吸附方式\"]},{\"menuName\":\"瓶口分液器\",\"names\":[\"量程范围\",\"安全阀\",\"产地\",\"分液方式\",\"应用\"]},{\"menuName\":\"塑料移液管\",\"names\":[\"容量\",\"是否消毒\",\"产地\"]},{\"menuName\":\"滴定器\",\"names\":[\"产地\"]},{\"menuName\":\"连续分液器\",\"names\":[\"类型\",\"产地\"]},{\"menuName\":\"加样槽\",\"names\":[\"是否消毒\",\"容量\",\"产地\"]},{\"menuName\":\"移液器架\",\"names\":[\"放置数量\",\"外观\",\"产地\"]},{\"menuName\":\"吸头盒\",\"names\":[\"孔数\",\"型号\",\"产地\"]},{\"menuName\":\"可调定量加液器\",\"names\":[\"瓶体规格\",\"刻度杆规格\",\"瓶体材质\",\"瓶体颜色\",\"产地\"]},{\"menuName\":\"封口膜\",\"names\":[\"类型\",\"产地\"]},{\"menuName\":\"试管塞\",\"names\":[]},{\"menuName\":\"磁力搅拌子\",\"names\":[\"形状\",\"产地\",\"最大尺寸范围\",\"直径\"]},{\"menuName\":\"反口胶塞\",\"names\":[\"尺寸\",\"产地\",\"材质\"]},{\"menuName\":\"生料带\",\"names\":[]},{\"menuName\":\"真空油脂\",\"names\":[\"产地\",\"适用温度\",\"适用真空度\"]},{\"menuName\":\"吸磁棒\",\"names\":[\"直径（mm）\",\"长度（mm）\",\"产地\"]},{\"menuName\":\"硅胶塞\",\"names\":[\"适用口径\",\"产地\",\"用途\"]},{\"menuName\":\"橡胶塞\",\"names\":[\"上径×下径×高\",\"产地\",\"型号\"]},{\"menuName\":\"搅拌棒\",\"names\":[\"长度\",\"材质\",\"产地\"]},{\"menuName\":\"四氟搅拌塞\",\"names\":[\"产地\",\"口径\"]},{\"menuName\":\"O形圈\",\"names\":[\"产地\",\"材质\"]},{\"menuName\":\"搅拌器套管\",\"names\":[\"产地\",\"类型\",\"磨口\"]},{\"menuName\":\"玻璃空心塞·玻璃实心塞\",\"names\":[\"产地\",\"型号\",\"类型\"]},{\"menuName\":\"电炉\",\"names\":[]},{\"menuName\":\"电加热圈\",\"names\":[\"直径\",\"类型\",\"产地\"]},{\"menuName\":\"石棉网\",\"names\":[\"边长\",\"产地\"]},{\"menuName\":\"玻璃温度计\",\"names\":[\"类型\",\"材质\",\"温度范围\",\"是否磨口\",\"长度\",\"产地\",\"精度\"]},{\"menuName\":\"温湿度计\",\"names\":[]},{\"menuName\":\"冷肼\",\"names\":[\"类型\",\"冷肼形状\",\"磨口下有效长度\",\"磨口大小\",\"产地\"]},{\"menuName\":\"本生灯\",\"names\":[\"填充燃料\",\"产地\"]},{\"menuName\":\"酒精灯·灯芯\",\"names\":[]},{\"menuName\":\"燃烧勺\",\"names\":[]},{\"menuName\":\"石英舟\",\"names\":[\"长度\",\"产地\",\"耳朵数\"]},{\"menuName\":\"瓷舟\",\"names\":[\"尺寸\",\"产地\"]},{\"menuName\":\"方舟架\",\"names\":[\"产地\"]},{\"menuName\":\"杜瓦瓶\",\"names\":[]},{\"menuName\":\"冰袋\",\"names\":[]},{\"menuName\":\"石棉阻燃布\",\"names\":[]},{\"menuName\":\"温度计套管\",\"names\":[\"口径\",\"长度\",\"类别\",\"产地\"]},{\"menuName\":\"冷藏箱\",\"names\":[]},{\"menuName\":\"保冷剂\",\"names\":[]},{\"menuName\":\"防暴沸玻璃珠\",\"names\":[\"直径\",\"产地\"]},{\"menuName\":\"过滤瓶\",\"names\":[\"磨口\",\"产地\",\"类别\",\"容量\"]},{\"menuName\":\"漏斗\",\"names\":[\"材质\",\"容量\",\"漏斗类别\",\"产地\",\"直径\",\"类型\"]},{\"menuName\":\"砂芯滤球·具砂板过滤管\",\"names\":[\"产地\",\"直径\",\"砂板型号\",\"磨口\"]},{\"menuName\":\"单链球·双链球\",\"names\":[]},{\"menuName\":\"脱脂棉\",\"names\":[]},{\"menuName\":\"分水器\",\"names\":[\"塞子材质\",\"容积\",\"产地\"]},{\"menuName\":\"过滤皮碗\",\"names\":[\"颜色\",\"产地\",\"包装\"]},{\"menuName\":\"不锈钢筛子\",\"names\":[\"目数/孔径\",\"产地\",\"直径×内高\",\"类别\"]},{\"menuName\":\"研钵\",\"names\":[]},{\"menuName\":\"索氏提取器\",\"names\":[\"接收器容量\",\"抽出筒下口\",\"接收器磨口\",\"抽出筒上口\",\"冷凝器磨口\",\"产地\"]},{\"menuName\":\"漏斗架\",\"names\":[\"产地\"]},{\"menuName\":\"层析柱\",\"names\":[\"外径\",\"长度\",\"有无砂芯\",\"产地\",\"有无储存球\",\"储存球容量\",\"磨口\"]},{\"menuName\":\"尼龙滤网\",\"names\":[\"目数\",\"产地\"]},{\"menuName\":\"砂芯坩埚\",\"names\":[\"容量\",\"砂板\"]},{\"menuName\":\"干燥器\",\"names\":[\"颜色\",\"尺寸\",\"类别\",\"产地\",\"材质\"]},{\"menuName\":\"坩埚\",\"names\":[\"产地\",\"容量\",\"类型\"]},{\"menuName\":\"结晶皿\",\"names\":[\"特点\",\"产地\",\"直径\"]},{\"menuName\":\"蒸发皿\",\"names\":[\"产地\",\"直径\",\"容量\",\"材质\"]},{\"menuName\":\"表面皿\",\"names\":[\"直径\",\"产地\",\"材质\"]},{\"menuName\":\"吹风机\",\"names\":[\"档位\",\"产地\",\"折叠\",\"功率\"]},{\"menuName\":\"坩埚钳\",\"names\":[\"产地\",\"类型\"]},{\"menuName\":\"坩埚架\",\"names\":[\"孔径\",\"孔数\"]},{\"menuName\":\"气体干燥塔\",\"names\":[\"规格\",\"产地\"]},{\"menuName\":\"干燥管\",\"names\":[\"类型\",\"产地\",\"磨口\"]},{\"menuName\":\"塑料洗瓶\",\"names\":[\"类型\",\"瓶口类型\",\"瓶口类型\",\"容量\",\"类型\",\"产地\"]},{\"menuName\":\"气球\",\"names\":[]},{\"menuName\":\"刷子\",\"names\":[\"产地\",\"适用容器\"]},{\"menuName\":\"废液桶\",\"names\":[\"容量\",\"类别\",\"产地\",\"外形\"]},{\"menuName\":\"氧气袋\",\"names\":[]},{\"menuName\":\"多孔式气体洗瓶\",\"names\":[]},{\"menuName\":\"酸缸/碱缸\",\"names\":[\"长×宽×高（cm）\",\"容积\",\"厚度\",\"产地\",\"材质\"]},{\"menuName\":\"球胆/气囊\",\"names\":[]},{\"menuName\":\"细胞培养皿\",\"names\":[\"直径\",\"是否灭菌\",\"产地\",\"表面类型\"]},{\"menuName\":\"细胞培养板\",\"names\":[\"产地\",\"表面类型\",\"颜色\",\"孔数\",\"底面类型\",\"是否灭菌\"]},{\"menuName\":\"细胞培养瓶\",\"names\":[\"产地\",\"规格（面积或容积）\",\"瓶盖类型\",\"瓶型\",\"培养方式\",\"瓶颈类型\",\"材质\"]},{\"menuName\":\"玻璃培养皿\",\"names\":[\"产地\",\"直径\"]},{\"menuName\":\"细胞刮刀/刮铲\",\"names\":[\"是否消毒\",\"产地\",\"刮片长\",\"柄长\"]},{\"menuName\":\"细胞筛网\",\"names\":[\"孔径\",\"颜色\",\"产地\"]},{\"menuName\":\"培养皿消毒桶\",\"names\":[\"直径\",\"产地\"]},{\"menuName\":\"培养皿架\",\"names\":[]},{\"menuName\":\"玻底培养皿\",\"names\":[\"表面类型\",\"直径\",\"是否灭菌\"]},{\"menuName\":\"玻底培养板\",\"names\":[\"是否灭菌\",\"孔数\",\"孔径\",\"表面类型\"]},{\"menuName\":\"培养基瓶/血清瓶\",\"names\":[\"容量\",\"瓶体材质\",\"产地\",\"灭菌方式\"]},{\"menuName\":\"爬片\",\"names\":[\"适用培养板\",\"直径\"]},{\"menuName\":\"细胞工厂\",\"names\":[]},{\"menuName\":\"细菌培养皿\",\"names\":[\"类型\",\"直径\",\"产地\",\"是否灭菌\"]},{\"menuName\":\"接种环\",\"names\":[]},{\"menuName\":\"接种针\",\"names\":[]},{\"menuName\":\"涂布棒\",\"names\":[]},{\"menuName\":\"接种棒\",\"names\":[]},{\"menuName\":\"接种针·接种环消毒器\",\"names\":[\"产地\"]},{\"menuName\":\"冻存管\",\"names\":[\"产地\",\"容量\",\"底部形状\",\"是否灭菌\",\"材质\",\"旋塞类型\"]},{\"menuName\":\"离心管盒\",\"names\":[\"材质\",\"适用离心管\",\"孔数\"]},{\"menuName\":\"PCR封板膜\",\"names\":[]},{\"menuName\":\"PCR板\",\"names\":[]},{\"menuName\":\"离心管\",\"names\":[\"产地\",\"品类\",\"规格\",\"底部形状\",\"盖子类型\",\"是否灭菌\"]},{\"menuName\":\"PCR管\",\"names\":[]},{\"menuName\":\"冻存盒\",\"names\":[]},{\"menuName\":\"冻存盖子色标\",\"names\":[]},{\"menuName\":\"离心管架\",\"names\":[\"适用离心管\",\"孔数\",\"材质\"]},{\"menuName\":\"酶标板\",\"names\":[\"规格\",\"颜色\",\"表面特性\",\"是否可拆\",\"材质\",\"底部\",\"是否灭菌\",\"是否带盖\",\"产地\"]},{\"menuName\":\"酶标条\",\"names\":[\"规格\",\"颜色\",\"表面特性\",\"底部\",\"是否灭菌\",\"是否带盖\",\"产地\"]},{\"menuName\":\"酶标板框\",\"names\":[]},{\"menuName\":\"离心瓶\",\"names\":[\"瓶盖材质\",\"产地\",\"容量\",\"瓶体材质\"]},{\"menuName\":\"PCR冰盒\",\"names\":[\"温度\",\"孔数\",\"适用尺寸\",\"产地\"]},{\"menuName\":\"载玻片\",\"names\":[]},{\"menuName\":\"盖玻片\",\"names\":[\"宽度\",\"长度\",\"厚度\",\"产地\"]},{\"menuName\":\"深孔板\",\"names\":[\"底部形状\",\"孔形\",\"容量\",\"是否灭菌\",\"产地\",\"孔数\",\"颜色\",\"材质\"]},{\"menuName\":\"深孔板封板膜\",\"names\":[\"适用深孔板孔数\",\"是否灭菌\",\"产地\"]},{\"menuName\":\"透析袋\",\"names\":[\"压平宽度\",\"直径\",\"截留分子量\",\"产地\",\"单位长度体积\",\"膜材质\"]},{\"menuName\":\"透析袋夹子\",\"names\":[]},{\"menuName\":\"包埋盒\",\"names\":[]},{\"menuName\":\"生物垃圾袋\",\"names\":[]},{\"menuName\":\"制冷·降温\",\"names\":[\"类别\",\"产地\",\"类别\",\"产地\"]},{\"menuName\":\"针式滤器\",\"names\":[\"产地\",\"直径\",\"孔径\",\"膜材质\",\"是否无菌\",\"适用溶液\"]},{\"menuName\":\"滤纸\",\"names\":[\"直径/边长\",\"流速\",\"产地\",\"特性\",\"材质\"]},{\"menuName\":\"加液器\",\"names\":[\"是否带针头\",\"容量\",\"是否灭菌\",\"类型\",\"是否带胶头\",\"是否螺口\",\"材质\",\"产地\"]},{\"menuName\":\"滤膜\",\"names\":[\"产地\",\"直径\",\"孔径\",\"适用溶液\"]},{\"menuName\":\"过滤器及配件\",\"names\":[\"容量\",\"滤膜材质\",\"类别\",\"产地\",\"材质\"]},{\"menuName\":\"固相萃取柱/SPE小柱\",\"names\":[\"容量\",\"填料\",\"产地\"]},{\"menuName\":\"液相色谱柱\",\"names\":[]},{\"menuName\":\"气相色谱柱\",\"names\":[]},{\"menuName\":\"进样瓶及配件\",\"names\":[\"直径\",\"适用容量\",\"类别\",\"瓶口\",\"材质\",\"颜色\",\"产地\"]},{\"menuName\":\"进样针\",\"names\":[\"针头\",\"容量\",\"用途\",\"使用\",\"存液\",\"产地\"]},{\"menuName\":\"顶空瓶及配件\",\"names\":[\"产地\",\"瓶口\",\"类型\",\"直径\"]},{\"menuName\":\"液相色谱耗材\",\"names\":[]},{\"menuName\":\"气相色谱耗材\",\"names\":[]},{\"menuName\":\"内插管\",\"names\":[\"容量\",\"瓶口\",\"形状\",\"产地\"]},{\"menuName\":\"滴定管\",\"names\":[\"量程\",\"精度\",\"活塞材质\",\"滴定方式\",\"颜色\",\"应用\",\"管体材质\",\"产地\"]},{\"menuName\":\"硅胶板\",\"names\":[]},{\"menuName\":\"核磁管\",\"names\":[\"类别\",\"外径\",\"产地\"]},{\"menuName\":\"比色皿\",\"names\":[\"光程\",\"样品量\",\"盖子类型\",\"产地\",\"材质\",\"容积\"]},{\"menuName\":\"比色管\",\"names\":[\"体积\",\"产地\",\"塞子\",\"类型\"]},{\"menuName\":\"试纸\",\"names\":[\"类型\",\"测量范围\",\"产地\"]},{\"menuName\":\"层析缸\",\"names\":[\"产地\",\"类型\",\"长*高\"]},{\"menuName\":\"染色缸\",\"names\":[]},{\"menuName\":\"比重瓶\",\"names\":[\"容量\",\"产地\"]},{\"menuName\":\"滴定台\",\"names\":[\"材质\",\"尺寸\",\"产地\"]},{\"menuName\":\"玻璃皂膜流量计\",\"names\":[\"容积\",\"产地\"]},{\"menuName\":\"核磁管架\",\"names\":[\"规格\",\"产地\"]},{\"menuName\":\"不锈钢剪刀\",\"names\":[\"长度\",\"产地\",\"类型\"]},{\"menuName\":\"玻璃刀\",\"names\":[\"裁划厚度\",\"产地\"]},{\"menuName\":\"止血钳\",\"names\":[\"尺寸\",\"类型\",\"产地\"]},{\"menuName\":\"腰椎穿刺针\",\"names\":[]},{\"menuName\":\"盐水放气针\",\"names\":[]},{\"menuName\":\"血球计数板\",\"names\":[]},{\"menuName\":\"采血管\",\"names\":[]},{\"menuName\":\"医疗锐器盒\",\"names\":[\"容积\",\"形状\"]},{\"menuName\":\"医用纱布\",\"names\":[\"类型\",\"规格\",\"产地\"]},{\"menuName\":\"白大褂/实验衣\",\"names\":[\"颜色\",\"袖长\",\"应用\",\"款式\",\"材质\",\"型号\"]},{\"menuName\":\"手套\",\"names\":[\"型号\",\"材质\",\"描述\",\"颜色\",\"应用\",\"包装规格\",\"产地\"]},{\"menuName\":\"口罩\",\"names\":[\"应用\",\"类型\",\"等级\",\"佩戴方式\",\"包装\"]},{\"menuName\":\"防护眼镜\",\"names\":[\"类别\",\"镜片颜色\",\"可否佩戴近视眼镜使用\"]},{\"menuName\":\"防护面屏\",\"names\":[\"类型\",\"产地\"]},{\"menuName\":\"滤毒盒\",\"names\":[\"适配系列\",\"产地\"]},{\"menuName\":\"安全鞋\",\"names\":[\"类别\",\"尺码\",\"颜色\"]},{\"menuName\":\"鞋套\",\"names\":[]},{\"menuName\":\"条形帽\",\"names\":[\"颜色\",\"未展开时长度\",\"产地\"]},{\"menuName\":\"耳塞\",\"names\":[\"产地\",\"是否带线\",\"形状\"]},{\"menuName\":\"洗眼器·洗眼液\",\"names\":[]},{\"menuName\":\"防护服\",\"names\":[]},{\"menuName\":\"防护面具配件\",\"names\":[\"产地\",\"适配系列\",\"品类\"]},{\"menuName\":\"面罩\",\"names\":[\"系列\",\"类别\",\"产地\"]},{\"menuName\":\"急救箱包\",\"names\":[\"类型\"]},{\"menuName\":\"擦镜纸\",\"names\":[\"产地\",\"尺寸\"]},{\"menuName\":\"擦拭纸\",\"names\":[]},{\"menuName\":\"擦拭布\",\"names\":[\"形状\",\"类型\",\"产地\"]},{\"menuName\":\"化学吸液垫\",\"names\":[]},{\"menuName\":\"橡皮筋\",\"names\":[]},{\"menuName\":\"马夹袋\",\"names\":[\"颜色\",\"产地\",\"尺寸\"]},{\"menuName\":\"胶带\",\"names\":[\"产地\",\"宽度\",\"类型\"]},{\"menuName\":\"接线板\",\"names\":[\"全长\",\"插孔数量\",\"产地\"]},{\"menuName\":\"打孔器\",\"names\":[]},{\"menuName\":\"计算器\",\"names\":[\"类别\",\"电池\",\"产地\"]},{\"menuName\":\"抽纸\",\"names\":[\"规格\",\"产地\"]},{\"menuName\":\"拖把\",\"names\":[]},{\"menuName\":\"垃圾袋\",\"names\":[\"大小\",\"产地\"]},{\"menuName\":\"洗手液\",\"names\":[]},{\"menuName\":\"卫生纸\",\"names\":[]},{\"menuName\":\"擦手纸\",\"names\":[\"产地\",\"尺寸\"]},{\"menuName\":\"座垫纸\",\"names\":[]},{\"menuName\":\"推车\",\"names\":[\"产地\",\"尺寸\",\"承重\",\"类型\"]},{\"menuName\":\"垃圾桶\",\"names\":[]},{\"menuName\":\"洗洁精·去污粉\",\"names\":[\"类型\"]},{\"menuName\":\"消毒液\",\"names\":[\"类型\",\"产地\"]},{\"menuName\":\"空气清香剂\",\"names\":[\"产地\"]},{\"menuName\":\"卷纸\",\"names\":[\"有无卷芯\",\"产地\"]},{\"menuName\":\"医用胶带·创可贴\",\"names\":[\"类型\"]},{\"menuName\":\"铝箔纸\",\"names\":[]},{\"menuName\":\"秒表\",\"names\":[\"通道数\",\"产地\"]},{\"menuName\":\"标签色带\",\"names\":[\"产地\",\"底色\",\"色带宽度\"]},{\"menuName\":\"老虎钳\",\"names\":[\"是否绝缘\",\"尺寸\",\"产地\"]},{\"menuName\":\"台面保护垫\",\"names\":[]},{\"menuName\":\"千分尺·游标卡尺\",\"names\":[\"产地\",\"类型\",\"测量范围\",\"精度\"]},{\"menuName\":\"尼龙扎带\",\"names\":[\"类别\",\"宽×长\",\"产地\"]},{\"menuName\":\"锤子\",\"names\":[]},{\"menuName\":\"水\",\"names\":[\"类型\"]},{\"menuName\":\"医用棉签\",\"names\":[]},{\"menuName\":\"手动压盖器·手动启盖器\",\"names\":[\"类别\",\"用途\",\"适用盖子尺寸\",\"产地\"]},{\"menuName\":\"保鲜膜·保鲜袋\",\"names\":[\"类别\",\"产地\"]},{\"menuName\":\"热风枪\",\"names\":[]},{\"menuName\":\"实验台\",\"names\":[]},{\"menuName\":\"天平台\",\"names\":[]},{\"menuName\":\"烘箱台·马弗炉底座\",\"names\":[]},{\"menuName\":\"桌·椅子\",\"names\":[]},{\"menuName\":\"工作台\",\"names\":[]},{\"menuName\":\"试剂柜\",\"names\":[]},{\"menuName\":\"器皿柜\",\"names\":[]},{\"menuName\":\"气瓶柜\",\"names\":[]},{\"menuName\":\"安全柜\",\"names\":[]},{\"menuName\":\"密集柜\",\"names\":[]},{\"menuName\":\"文件柜\",\"names\":[]},{\"menuName\":\"收纳柜\",\"names\":[]},{\"menuName\":\"展示柜\",\"names\":[]},{\"menuName\":\"货架\",\"names\":[]},{\"menuName\":\"管路接头\",\"names\":[]},{\"menuName\":\"洗眼器\",\"names\":[]},{\"menuName\":\"紧急喷淋装置\",\"names\":[]},{\"menuName\":\"试剂架\",\"names\":[]},{\"menuName\":\"合成架\",\"names\":[]},{\"menuName\":\"气体考克\",\"names\":[]},{\"menuName\":\"水龙头\",\"names\":[]},{\"menuName\":\"水杯·水槽\",\"names\":[]},{\"menuName\":\"滴水架\",\"names\":[\"材质\"]},{\"menuName\":\"压力表\",\"names\":[]},{\"menuName\":\"台式通风橱\",\"names\":[]},{\"menuName\":\"落地式通风橱\",\"names\":[]},{\"menuName\":\"万向抽气罩\",\"names\":[]},{\"menuName\":\"原子吸收罩\",\"names\":[]},{\"menuName\":\"格氏试剂\",\"names\":[]},{\"menuName\":\"有机锂试剂\",\"names\":[]},{\"menuName\":\"有机膦试剂\",\"names\":[]},{\"menuName\":\"钯催化剂\",\"names\":[]},{\"menuName\":\"铂催化剂\",\"names\":[]},{\"menuName\":\"金属吸附剂\",\"names\":[]},{\"menuName\":\"缩合试剂\",\"names\":[]},{\"menuName\":\"保护试剂\",\"names\":[]},{\"menuName\":\"硅烷试剂\",\"names\":[]},{\"menuName\":\"路易斯酸\",\"names\":[]},{\"menuName\":\"相转移催化剂\",\"names\":[]},{\"menuName\":\"三氟甲基苯化合物\",\"names\":[]},{\"menuName\":\"三氟甲氧基化合物\",\"names\":[]},{\"menuName\":\"全氟取代化合物\",\"names\":[]},{\"menuName\":\"硫醇·硫醚\",\"names\":[]},{\"menuName\":\"溴代苯硼酸\",\"names\":[]},{\"menuName\":\"杂环硼酸\",\"names\":[]},{\"menuName\":\"螺环化合物\",\"names\":[]},{\"menuName\":\"甘氨酸\",\"names\":[]},{\"menuName\":\"丙氨酸\",\"names\":[]},{\"menuName\":\"谷氨酸\",\"names\":[]},{\"menuName\":\"苯丙氨酸\",\"names\":[]},{\"menuName\":\"脯氨酸\",\"names\":[]},{\"menuName\":\"亮氨酸\",\"names\":[]},{\"menuName\":\"半胱氨酸·胱氨酸\",\"names\":[]},{\"menuName\":\"丝氨酸\",\"names\":[]},{\"menuName\":\"赖氨酸\",\"names\":[]},{\"menuName\":\"缬氨酸\",\"names\":[]},{\"menuName\":\"色氨酸\",\"names\":[]},{\"menuName\":\"富勒烯\",\"names\":[]},{\"menuName\":\"石墨烯与酸化石墨烯\",\"names\":[]},{\"menuName\":\"液晶材料\",\"names\":[]},{\"menuName\":\"液晶用联苯砌块\",\"names\":[]},{\"menuName\":\"电池用溶液\",\"names\":[]},{\"menuName\":\"电解质\",\"names\":[]},{\"menuName\":\"离子液体\",\"names\":[]},{\"menuName\":\"聚合物\",\"names\":[]},{\"menuName\":\"聚合引发剂\",\"names\":[]},{\"menuName\":\"阻聚剂\",\"names\":[]},{\"menuName\":\"聚合催化剂\",\"names\":[]},{\"menuName\":\"金属单质\",\"names\":[]},{\"menuName\":\"金属氧化物\",\"names\":[]},{\"menuName\":\"超高纯材料\",\"names\":[]},{\"menuName\":\"纳米碳材料\",\"names\":[]},{\"menuName\":\"纳米硅材料\",\"names\":[]},{\"menuName\":\"抗生素\",\"names\":[]},{\"menuName\":\"核糖·核苷\",\"names\":[]},{\"menuName\":\"酶·底物\",\"names\":[]},{\"menuName\":\"单糖\",\"names\":[]},{\"menuName\":\"寡糖\",\"names\":[]},{\"menuName\":\"多糖\",\"names\":[]},{\"menuName\":\"辅助试剂\",\"names\":[]},{\"menuName\":\"脂类\",\"names\":[]},{\"menuName\":\"DNA提取与纯化\",\"names\":[]},{\"menuName\":\"DNA电泳\",\"names\":[]},{\"menuName\":\"DNA标记与检测\",\"names\":[]},{\"menuName\":\"克隆与基因突变\",\"names\":[]},{\"menuName\":\"PCR相关\",\"names\":[]},{\"menuName\":\"菌种与感受态制备\",\"names\":[]},{\"menuName\":\"RNA相关\",\"names\":[]},{\"menuName\":\"血清\",\"names\":[]},{\"menuName\":\"细胞培养·转染\",\"names\":[]},{\"menuName\":\"细胞增殖·凋亡·坏死\",\"names\":[]},{\"menuName\":\"细胞自噬\",\"names\":[]},{\"menuName\":\"细胞组分分离\",\"names\":[]},{\"menuName\":\"细胞组织染色\",\"names\":[]},{\"menuName\":\"信号小分子检测\",\"names\":[]},{\"menuName\":\"荧光探针\",\"names\":[]},{\"menuName\":\"转录调控\",\"names\":[]},{\"menuName\":\"激活剂·抑制剂\",\"names\":[]},{\"menuName\":\"裂解及蛋白抽提\",\"names\":[]},{\"menuName\":\"蛋白纯化\",\"names\":[]},{\"menuName\":\"蛋白检测与定量\",\"names\":[]},{\"menuName\":\"Western Blot\",\"names\":[]},{\"menuName\":\"ELISA\",\"names\":[]},{\"menuName\":\"免疫沉淀\",\"names\":[]},{\"menuName\":\"免疫染色\",\"names\":[]},{\"menuName\":\"一抗\",\"names\":[]},{\"menuName\":\"二抗\",\"names\":[]},{\"menuName\":\"微生物培养基\",\"names\":[]},{\"menuName\":\"培养基原料\",\"names\":[]},{\"menuName\":\"培养基配套试剂\",\"names\":[]},{\"menuName\":\"其他检测试剂盒\",\"names\":[]},{\"menuName\":\"特优级无机盐\",\"names\":[]},{\"menuName\":\"药典级无机盐\",\"names\":[]},{\"menuName\":\"碳酸盐\",\"names\":[]},{\"menuName\":\"硝酸盐\",\"names\":[]},{\"menuName\":\"硫酸盐\",\"names\":[]},{\"menuName\":\"常用酸和碱\",\"names\":[]},{\"menuName\":\"钢桶装溶剂\",\"names\":[]},{\"menuName\":\"制备级溶剂\",\"names\":[]},{\"menuName\":\"超干溶剂\",\"names\":[]},{\"menuName\":\"液相色谱试剂\",\"names\":[]},{\"menuName\":\"气相色谱试剂\",\"names\":[]},{\"menuName\":\"LCMS试剂\",\"names\":[]},{\"menuName\":\"氘代试剂\",\"names\":[]},{\"menuName\":\"流动相添加物\",\"names\":[]},{\"menuName\":\"高纯酸\",\"names\":[]},{\"menuName\":\"缓冲溶液\",\"names\":[]},{\"menuName\":\"分析用盐类\",\"names\":[]},{\"menuName\":\"Redi-Dri™\",\"names\":[]},{\"menuName\":\"Analytical\",\"names\":[]},{\"menuName\":\"TraceSelect\",\"names\":[]},{\"menuName\":\"卡尔费休Hydranal\",\"names\":[]},{\"menuName\":\"标准溶液\",\"names\":[]},{\"menuName\":\"指示剂\",\"names\":[]},{\"menuName\":\"USP\",\"names\":[]},{\"menuName\":\"EP\",\"names\":[]},{\"menuName\":\"同位素标记物\",\"names\":[]},{\"menuName\":\"食品检测标准品\",\"names\":[]},{\"menuName\":\"环境检测标准品\",\"names\":[]},{\"menuName\":\"农药标准品\",\"names\":[]},{\"menuName\":\"兽药标准品\",\"names\":[]},{\"menuName\":\"工业标准品\",\"names\":[]},{\"menuName\":\"中药标准品\",\"names\":[]},{\"menuName\":\"消炎抗菌药\",\"names\":[]},{\"menuName\":\"抗肿瘤药物\",\"names\":[]},{\"menuName\":\"心血管药物\",\"names\":[]},{\"menuName\":\"内分泌药物\",\"names\":[]},{\"menuName\":\"鼻炎类药物\",\"names\":[]},{\"menuName\":\"骨科类药物\",\"names\":[]},{\"menuName\":\"其他原料药\",\"names\":[]},{\"menuName\":\"第二类易制毒化学品\",\"names\":[]},{\"menuName\":\"第三类易制毒化学品\",\"names\":[]},{\"menuName\":\"易制爆化学品\",\"names\":[]},{\"menuName\":\"精密天平\",\"names\":[\"是否是双量程\",\"内外校\",\"应用\",\"最大量程\",\"精度\",\"产地\"]},{\"menuName\":\"分析天平\",\"names\":[\"精度\",\"量程\",\"内外校\",\"单双量程\",\"产地\"]},{\"menuName\":\"防爆天平\",\"names\":[\"精度\",\"量程\",\"单双量程\",\"产地\"]},{\"menuName\":\"微量天平\",\"names\":[\"单双量程\",\"产地\",\"精度\"]},{\"menuName\":\"砝码\",\"names\":[]},{\"menuName\":\"天平相关配件\",\"names\":[\"配件类型\",\"产地\"]},{\"menuName\":\"磁力搅拌器\",\"names\":[\"搅拌点数\",\"控温范围 [盘面][℃]\",\"最大转速[rpm]\",\"最大搅拌量[L]\",\"面板材质\"]},{\"menuName\":\"顶置式搅拌器\",\"names\":[\"最大转速[rpm]\",\"最大搅拌量[L]\"]},{\"menuName\":\"离心机\",\"names\":[\"特征参数\",\"功能\",\"产地\",\"类型\"]},{\"menuName\":\"超声波细胞破碎仪\",\"names\":[]},{\"menuName\":\"摇床/振荡器\",\"names\":[\"产地\",\"振荡方式\",\"最低温度（℃）\",\"最高温度（℃）\"]},{\"menuName\":\"分散机\",\"names\":[\"最大转速[rpm]\",\"最大处理量[ml]\"]},{\"menuName\":\"研磨粉碎机\",\"names\":[]},{\"menuName\":\"球磨机\",\"names\":[]},{\"menuName\":\"均质器\",\"names\":[\"是否带加热\",\"最大处理量[ml]\",\"是否带加热\",\"最大处理量[ml]\",\"是否带加热\",\"最大处理量[ml]\"]},{\"menuName\":\"磁力搅拌器配件\",\"names\":[\"配件类型\"]},{\"menuName\":\"顶置式搅拌器配件\",\"names\":[]},{\"menuName\":\"分散机配件\",\"names\":[\"配件类型\"]},{\"menuName\":\"摇床/振荡器配件\",\"names\":[]},{\"menuName\":\"干燥箱/烘箱\",\"names\":[\"产地\",\"最高控温温度[℃]\",\"容积[L]\",\"产品类型\"]},{\"menuName\":\"真空泵\",\"names\":[\"极限压力（mbar）\",\"抽气速率（m3/h）\",\"产地\",\"类型\"]},{\"menuName\":\"蠕动泵\",\"names\":[\"类型\",\"通道数\",\"最大流量（ml/min）\",\"产地\"]},{\"menuName\":\"培养箱\",\"names\":[\"产地\",\"最高控温温度[℃]\",\"容积[L]\",\"温度波动度[℃]\",\"产品类型\"]},{\"menuName\":\"试验箱\",\"names\":[\"产地\",\"最高控温温度[℃]\",\"容积[L]\",\"产品类型\"]},{\"menuName\":\"注射泵\",\"names\":[\"通道数\",\"最大流量（ml/min）\",\"类型\"]},{\"menuName\":\"蠕动泵配件\",\"names\":[\"类型\"]},{\"menuName\":\"防潮箱\",\"names\":[\"湿度控制范围(RH)\",\"容积（L）\",\"类型\",\"产地\"]},{\"menuName\":\"旋转蒸发仪\",\"names\":[\"蒸发瓶(L)\",\"控温范围（℃）\",\"升降方式\",\"产地\"]},{\"menuName\":\"冷冻干燥机\",\"names\":[\"产地\",\"冻干面积（m2）\",\"加热\",\"类型\",\"空载冷阱温度（℃）\",\"捕水能力（kg）\"]},{\"menuName\":\"氮吹仪\",\"names\":[]},{\"menuName\":\"紫外分析仪\",\"names\":[\"类型\",\"波长（nm）\"]},{\"menuName\":\"反应釜\",\"names\":[\"产地\",\"类型\",\"物料容积(L)\"]},{\"menuName\":\"反应合成仪\",\"names\":[]},{\"menuName\":\"旋转蒸发仪配件\",\"names\":[\"配件类型\"]},{\"menuName\":\"超声波清洗器\",\"names\":[\"产地\",\"类型\",\"排水\",\"容积(L)\",\"加热功能\",\"功率可调\"]},{\"menuName\":\"纯水·超纯水机\",\"names\":[]},{\"menuName\":\"灭菌器\",\"names\":[\"产地\",\"开盖方式\",\"最高灭菌温度(℃)\",\"功能\",\"样式\",\"有效容积(L)\"]},{\"menuName\":\"除湿机\",\"names\":[]},{\"menuName\":\"洗瓶机\",\"names\":[]},{\"menuName\":\"蒸馏水器\",\"names\":[\"产地\",\"纯度\",\"出水量（L/h）\",\"类型\"]},{\"menuName\":\"低温冰箱\",\"names\":[\"产地\",\"外观形状\",\"最低温度（℃）\",\"容积（L）\",\"产品类型\"]},{\"menuName\":\"液氮罐\",\"names\":[\"产地\",\"容积（L）\",\"口径（mm）\"]},{\"menuName\":\"层析柜\",\"names\":[\"产地\"]},{\"menuName\":\"防爆冰箱\",\"names\":[]},{\"menuName\":\"电热板\",\"names\":[\"产地\",\"面板材质\"]},{\"menuName\":\"水/油浴\",\"names\":[\"类型\",\"产地\",\"最高温度（℃）\"]},{\"menuName\":\"电热套\",\"names\":[\"温度显示\",\"容积（mL）\"]},{\"menuName\":\"制冰机\",\"names\":[\"制冰量(kg/24h)\",\"产地\",\"储冰量(kg)\"]},{\"menuName\":\"电阻炉/马弗炉\",\"names\":[\"最高温度（℃）\",\"可编程\",\"产地\",\"体积（L）\"]},{\"menuName\":\"管式炉\",\"names\":[\"可编程\",\"产地\",\"最高温度（℃）\"]},{\"menuName\":\"加热浴槽循环器\",\"names\":[\"浴槽体积(L)\",\"循环泵流量(L/min)\",\"产地\",\"最高温度(℃)\",\"最低温度(℃)\"]},{\"menuName\":\"金属浴\",\"names\":[\"产地\",\"标配模块（个）\",\"最高温度（℃）\",\"最低温度（℃）\"]},{\"menuName\":\"制冷恒温器\",\"names\":[\"产地\",\"最低温度(℃)\",\"浴槽体积(L)\",\"循环泵流量(L/min)\",\"最高温度(℃)\",\"是否带搅拌\"]},{\"menuName\":\"PH计\",\"names\":[]},{\"menuName\":\"电导率仪\",\"names\":[]},{\"menuName\":\"电化学分析\",\"names\":[]},{\"menuName\":\"电极\",\"names\":[]},{\"menuName\":\"电位滴定\",\"names\":[]},{\"menuName\":\"其他滴定设备\",\"names\":[]},{\"menuName\":\"离子计\",\"names\":[]},{\"menuName\":\"电化学相关配件\",\"names\":[]},{\"menuName\":\"梅特勒试剂\",\"names\":[]},{\"menuName\":\"熔点仪\",\"names\":[]},{\"menuName\":\"粘度计\",\"names\":[]},{\"menuName\":\"水分测定仪\",\"names\":[]},{\"menuName\":\"温湿度测量\",\"names\":[]},{\"menuName\":\"密度计\",\"names\":[]},{\"menuName\":\"热分析仪器\",\"names\":[]},{\"menuName\":\"质构仪\",\"names\":[]},{\"menuName\":\"流变仪\",\"names\":[]},{\"menuName\":\"激光粒度仪\",\"names\":[]},{\"menuName\":\"澄明度检测仪\",\"names\":[]},{\"menuName\":\"电泳仪\",\"names\":[\"仪器类型\",\"产地\"]},{\"menuName\":\"溶出仪\",\"names\":[]},{\"menuName\":\"崩解仪\",\"names\":[]},{\"menuName\":\"硬度仪\",\"names\":[]},{\"menuName\":\"脆度仪\",\"names\":[]},{\"menuName\":\"凝胶染色仪\",\"names\":[]},{\"menuName\":\"旋光仪\",\"names\":[]},{\"menuName\":\"折光仪/折射仪\",\"names\":[]},{\"menuName\":\"显微镜\",\"names\":[\"产地\",\"种类\"]},{\"menuName\":\"盐度计\",\"names\":[]},{\"menuName\":\"光泽度计\",\"names\":[]},{\"menuName\":\"凝胶成像\",\"names\":[]},{\"menuName\":\"气体发生器\",\"names\":[]},{\"menuName\":\"红外分光光度计\",\"names\":[]},{\"menuName\":\"紫外分光光度计\",\"names\":[\"产地\",\"结构\",\"检测器\",\"波长范围\"]},{\"menuName\":\"荧光分光光度计\",\"names\":[]},{\"menuName\":\"液相色谱仪\",\"names\":[]},{\"menuName\":\"气相色谱仪\",\"names\":[]},{\"menuName\":\"光谱·色谱配件\",\"names\":[]},{\"menuName\":\"UPS/不间断电源\",\"names\":[]},{\"menuName\":\"溶解氧检测仪\",\"names\":[]},{\"menuName\":\"多参数检测仪\",\"names\":[]},{\"menuName\":\"BOD·COD·TOC检测仪\",\"names\":[]},{\"menuName\":\"浊度仪\",\"names\":[]},{\"menuName\":\"哈希试剂\",\"names\":[]},{\"menuName\":\"凯式定氮仪\",\"names\":[]},{\"menuName\":\"脂肪测定仪\",\"names\":[]},{\"menuName\":\"纤维测定仪\",\"names\":[]},{\"menuName\":\"消化炉\",\"names\":[]},{\"menuName\":\"制备器\",\"names\":[\"类型\"]},{\"menuName\":\"细度计\",\"names\":[]},{\"menuName\":\"涂布器\",\"names\":[]},{\"menuName\":\"涂膜机\",\"names\":[]},{\"menuName\":\"湿膜厚度规\",\"names\":[]},{\"menuName\":\"砂磨机\",\"names\":[]},{\"menuName\":\"耐摩擦试验机\",\"names\":[]},{\"menuName\":\"涂料·油墨专用仪器\",\"names\":[]},{\"menuName\":\"涂料·油墨专用耗材\",\"names\":[]},{\"menuName\":\"研发综合管理\",\"names\":[]},{\"menuName\":\"库存采购管理\",\"names\":[]},{\"menuName\":\"危险品管理\",\"names\":[]},{\"menuName\":\"文档管理\",\"names\":[]},{\"menuName\":\"经费管理\",\"names\":[]},{\"menuName\":\"设备管理\",\"names\":[]},{\"menuName\":\"质量管理\",\"names\":[]},{\"menuName\":\"高校实验室综合管理\",\"names\":[]},{\"menuName\":\"经销商进销存管理\",\"names\":[]},{\"menuName\":\"LIMS系统\",\"names\":[]},{\"menuName\":\"iLab\",\"names\":[]},{\"menuName\":\"仪器连接软件（Limslink）\",\"names\":[]},{\"menuName\":\"Chemoffice\",\"names\":[]},{\"menuName\":\"E-Notebook\",\"names\":[]},{\"menuName\":\"化合物注册软件\",\"names\":[]},{\"menuName\":\"TIBCO Spotfire\",\"names\":[]}]";
        List<Property> propertyList = JSON.parseArray(pStr, Property.class);
        List<String> pNameList = new ArrayList<>();
        propertyList.parallelStream().map(Property::getNames).filter(l -> l.size() > 0).forEach(pNameList::addAll);
        pNameList = pNameList.parallelStream().distinct().filter(s -> s != null && !"".equals(s)).collect(Collectors.toList());
        String names = JSON.toJSONString(pNameList);
        System.out.println(names);
    }
}