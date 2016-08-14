package com.jiurong.mnote.search;

import java.util.Collection;
import java.util.List;

/**
 * 对索引服务调用方式说明：
 * 接口调用方要求：
 * １、调用方会异步的调用索引服务，保证不因为索引服务的响应速度而阻塞正常业务。
 * ２、调用方会保证调用在逻辑上的顺序一致性。比如对相同某个段落的前后三次修改操作，调用方会依照修改顺序逐次调用索引服务，等待索引服务返回后调用下一个索引服务。
 * 索引服务提供者要求：
 * １、调用方可能对逻辑上可以并发的操作并行建立索引。因此索引提供者应该支持并发的写操作。
 * ２、调用方按照正确的顺序调用索引服务，应该得到预期的索引结果。
 * 例如对某个段落的三次编辑操作按顺序调用，索引服务应该保证最后只有第三次编辑内容的索引。
 * 再比如先编辑再删除一个段落的索引，索引服务也应该保证最终这个段落相关的索引被删除。
 * 
 * 索引服务其它一些说明：
 * 索引服务实际是一个客户端——服务器结构，SearchService是一个封装好的客户端，调用实际的索引服务。
 * 这个客户端可能需要一些配置项，这些配置项应该以这样的方式来暴露给外部：
 * 应该提供一个SearchService的构造函数或者构建器。提供一个静态的build方法，这个方法接受所有需要传入的参数，并构造一个SearchService实例供上层应用使用。
 * @author ytm
 *
 */
public interface SearchService {

	/**
	 * 建立段落索引
	 * @param indexType 索引类型
	 * @param documentId 文档id
	 * @param paragraphId 段落id
	 * @param texts 段落纯文本内容，可能有多段内容。这部分内容需要分词并添加索引。
	 * @param keywords 检索关键字，这部分内容不应该分词，应该直接进行索引。
	 */
	void addParagraph(IndexType indexType, String documentId, String paragraphId, List<String> texts, List<String> keywords);
	
	/**
	 * 更新段落索引
	 * @param indexType 索引类型
	 * @param documentId 文档id
	 * @param paragraphId 段落id
	 * @param texts 段落纯文本内容，可能有多段内容。这部分内容需要分词并添加索引。
	 * @param keywords 检索关键字，这部分内容不应该分词，应该直接进行索引。
	 */
	void editParagraph(IndexType indexType, String documentId, String paragraphId, List<String> texts, List<String> keywords);
	
	/**
	 * 删除段落索引
	 * @param documentId 文档id
	 * @param paragraphId 段落id
	 */
	void deleteParagraph(String documentId, String paragraphId);
	
	/**
	 * 检索关键字匹配的文档
	 * @param keyword 查询文本
	 * @param documentIds 在查找范围内的文档id列表
	 * @return
	 */
	List<Index> search(String keyword, Collection<String> documentIds);
	
	/**
	 * 删除文档的全部索引
	 * @param documentId
	 */
	void deleteDocument(String documentId);
}
