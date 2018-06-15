<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapperPack}.${modelName}Dao" >
    <resultMap id="BaseResultMap" type="${modelPack}.${modelName}" >
    <#list fields as field>
        <#if field.name == "id">
       	<id column="${field.name}" property="${field.propertyName}" jdbcType="${field.type}" />
        <#else>
        <result column="${field.name}" property="${field.propertyName}" <#if field.propertyType!="Date">jdbcType="${field.type}"</#if> />
        </#if>
    </#list>
    </resultMap>
    <sql id="Base_Column_List" >
    <#list fields as field> ${field.name}<#if field_has_next>, </#if></#list>
    </sql>
    <!-- 查找记录 -->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap"  >
        select <include refid="Base_Column_List" />
        from  ${tableName}
        where  id = ${'#'}{id,jdbcType=INTEGER}
    </select>
    <!-- 查找所有记录 -->
    <select id="selectAll" resultMap="BaseResultMap" >
        select <include refid="Base_Column_List" />
        from  ${tableName}
    </select>
    <!-- 删除指定记录 -->
    <delete id="deleteByPrimaryKey"  >
        delete from  ${tableName}
        where  id = ${'#'}{id,jdbcType=INTEGER}
    </delete>
    <!-- 批量删除记录 -->
    <delete id="deleteByIds" >
        delete from  ${tableName}
        where  id in
        <foreach collection="ids" index="index" item="item" open="(" separator="," close=")">
        	${'#'}{item}
        </foreach>
    </delete>
    <!-- 批量查询记录 -->
    <select id="selectByIds" resultMap="BaseResultMap">
        select <include refid="Base_Column_List" />
        from  ${tableName}
        where  id in
        <foreach collection="ids" index="index" item="item" open="(" separator="," close=")">
        	${'#'}{item}
        </foreach>
    </select>
    <!-- 新增记录 -->
    <insert id="insert" parameterType="${modelPack}.${modelName}" useGeneratedKeys="true" keyProperty="id">
        insert into  ${tableName} (
    		<#list fields as field>${field.name}<#if field_has_next>, </#if></#list>
        )
        values (
    		<#list fields as field>${'#'}{${field.propertyName}<#if field.propertyType!="Date">,jdbcType=${field.type}</#if>}<#if field_has_next>, </#if></#list>
        )
    </insert>
    <!-- 新增记录 -->
    <insert id="insertSelective" parameterType="${modelPack}.${modelName}" useGeneratedKeys="true" keyProperty="id">
        insert into  ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides="," >
        <#list fields as field>
            <if test="${field.propertyName} != null" >
                 ${field.name},
            </if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides="," >
        <#list fields as field>
            <if test="${field.propertyName} != null" >
            ${'#'}{${field.propertyName}<#if field.propertyType!="Date">,jdbcType=${field.type}</#if>},
            </if>
        </#list>
        </trim>
    </insert>
    <!-- 修改指定记录 -->
    <update id="updateByPrimaryKeySelective" parameterType="${modelPack}.${modelName}" >
        update  ${tableName}
        <set>
        <#list fields as field>
            <#if field.propertyName != "id">
                <if test="${field.propertyName} != null" >
                     ${field.name} = ${'#'}{${field.propertyName}<#if field.propertyType!="Date">,jdbcType=${field.type}</#if>},
                </if>
            </#if>
        </#list>
        </set>
        where id = ${'#'}{id,jdbcType=INTEGER}
    </update>
    <!-- 修改指定记录 -->
    <update id="updateByPrimaryKey" parameterType="${modelPack}.${modelName}" >
        update  ${tableName}
        set
    <#list fields as field>
         ${field.name} = ${'#'}{${field.propertyName}<#if field.propertyType!="Date">,jdbcType=${field.type}</#if>}<#if field_has_next>, </#if>
    </#list>
        where id = ${'#'}{id,jdbcType=INTEGER}
    </update>
    <!-- 按条件查询(可分页) -->
    <select id="selectByParams" parameterType="java.util.Map" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from  ${tableName}
        <where>
        <#list fields as field>
            <if test="${field.propertyName} != null" >
                AND  ${field.name} = ${'#'}{${field.propertyName}}
            </if>
        </#list>
        </where>
        <if test="orderBy != null"> order by ${'$'}{orderBy} </if>
        <if test="startRow != null and pageSize != null"> limit ${'#'}{pageSize} offset ${'#'}{startRow}</if>
    </select>
    <!-- 按条件查询总数 -->
    <select id="selectCntByParams" parameterType="java.util.Map" resultType="java.lang.Integer">
        select
        count(*)
        from  ${tableName}
        <where>
        <#list fields as field>
            <if test="${field.propertyName} != null" >
                AND  ${field.name} = ${'#'}{${field.propertyName}}
            </if>
        </#list>
        </where>
    </select>
    <!-- 以下为自定义方法 -->
</mapper>