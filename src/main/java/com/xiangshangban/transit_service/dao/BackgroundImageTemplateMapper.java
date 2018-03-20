package com.xiangshangban.transit_service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xiangshangban.transit_service.bean.BackgroundImageTemplate;
import com.xiangshangban.transit_service.bean.Image;
import com.xiangshangban.transit_service.bean.Template;

@Mapper
public interface BackgroundImageTemplateMapper {
	
	/**
	 * 新增图片表
	 * @param image
	 * @return
	 */
	int insertImage(Image image);
	
	/**
	 * 新增模版表
	 * @param template
	 * @return
	 */
	int insertTemplate(Template template);
	
	/**
	 * 新增图片与模板关联表
	 * @param backgroundImageTemplate
	 * @return
	 */
	int insertBackgroundImageTemplate(BackgroundImageTemplate backgroundImageTemplate);
	
	/**
	 * 修改图片表
	 * @param image
	 * @return
	 */
	int updateImage(Image image);
	
	/**
	 * 修改模版表
	 * @param template
	 * @return
	 */
	int updateTemplate(Template template);
	
	/**
	 * 修改图片与模板关联表
	 * @param backgroundImageTemplate
	 * @return
	 */
	int updateBackgroundImageTemplate(BackgroundImageTemplate backgroundImageTemplate);
	
	/**
	 * 根据TemplateId 查询信息
	 * @param TemplateId
	 * @return
	 */
	BackgroundImageTemplate SelectByTemplateId(String templateId);
	
	/**
	 * 删除图片表
	 * @param id
	 * @return
	 */
	int deleteImage(String id);
	
	/**
	 * 删除模版表
	 * @param id
	 * @return
	 */
	int deleteTemplate(String id);
	
	/**
	 * 删除图片和模板关联表
	 * @param id
	 * @return
	 */
	int deleteBackgroundImageTemplate(String id);
	
	/**
	 * 按ID查询图片表
	 * @param id
	 * @return
	 */
	Image SelectImageById(String id);
	
	/**
	 * 按ID查询模板表
	 * @param id
	 * @return
	 */
	Template SelectTemplateById(String id);
	
	/**
	 * 按公司编号查询出该公司所有任务主题
	 * @param companyId
	 * @return
	 */
	List<BackgroundImageTemplate> FindAllByCompanyId(String companyId);
}
