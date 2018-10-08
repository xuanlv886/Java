package com.service.extra.mall.search;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.service.extra.mall.mapper.ProductMapper;

import util.IndexPath;
import util.Utils;



public class SearchPage {

	@Resource private ProductMapper productMapper;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> doSearch(Map<String, Object> map) throws IOException, ParseException {

		Path path = FileSystems.getDefault().getPath(IndexPath.PATH);
		// 创建Directory
		Directory directory = FSDirectory.open(path);
		// ���������鿴��
		IndexReader indexReader = DirectoryReader.open(directory);
		// ����������
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// ��������
		String looseName = ("" + map.get("looseName")).replaceAll("/", "");
		QueryParser parser = new QueryParser("searchWord", new IKAnalyzer());
		Query query = parser.parse(looseName);
		Builder builder = new BooleanQuery.Builder();
		/*
		 * if(map.get("marketId")!=null && !"".equals(map.get("marketId"))){
		 * TermQuery termQuery1 = new TermQuery(new
		 * Term("marketId",""+map.get("marketId")));
		 * builder.add(termQuery1,BooleanClause.Occur.MUST); }
		 */

		if (map.get("productTypeId") != null && !"".equals(map.get("productTypeId"))) {
			TermQuery termQuery2 = new TermQuery(new Term("typeId", "" + map.get("productTypeId")));
			builder.add(termQuery2, BooleanClause.Occur.MUST);
		}

		builder.add(query, BooleanClause.Occur.MUST);

		BooleanQuery booleanQuery = builder.build();

		// ��ҳ��Ϣ
		Integer start = Integer.valueOf("" + map.get("index"));
		Integer pageSize = Integer.valueOf("" + map.get("size"));
		Integer end = start + pageSize;

		// ���������ֶ�
		TopDocs topDocs = null;
		if (!Utils.isEmpty(""+map.get("priceSortType"))) {
			if (Integer.valueOf("" + map.get("priceSortType")) == 0) {
				Sort sort = new Sort(new SortField("proPrice", Type.DOUBLE, true));
				topDocs = indexSearcher.search(booleanQuery, end, sort);// 按照价格升序
			}

			if (Integer.valueOf("" + map.get("priceSortType")) == 1) {
				Sort sort = new Sort(new SortField("proPrice", Type.DOUBLE, false));
				topDocs = indexSearcher.search(booleanQuery, end, sort);// 按照价格降序
			}
		}

		if (!Utils.isEmpty(""+map.get("salesSortType"))) {
			if (Integer.valueOf("" + map.get("salesSortType")) == 0) {
				Sort sort = new Sort(new SortField("payNum", Type.LONG, true));
				topDocs = indexSearcher.search(booleanQuery, end, sort);// 按照销量升序
			}

			if (Integer.valueOf("" + map.get("salesSortType")) == 1) {
				Sort sort = new Sort(new SortField("payNum", Type.LONG, false));
				topDocs = indexSearcher.search(booleanQuery, end, sort);// 按照销量降序
			}
		}
		if (topDocs==null) {
			topDocs = indexSearcher.search(booleanQuery, end);
		}
		/*
		 * if (map.get("salesSortType").equals("0") &&
		 * map.get("priceSortType").equals("0")) { topDocs =
		 * indexSearcher.search(booleanQuery, end); }
		 */
		
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		int length = scoreDocs.length > end ? end : scoreDocs.length;
		Map<String, String> typeMap = new HashMap<String, String>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (int i = start; i < length; i++) {
			Map<String, Object> proMap = new HashMap<String, Object>();
			ScoreDoc doc = scoreDocs[i];
			Document document = indexSearcher.doc(doc.doc);
			proMap.put("productId", document.get("id"));
			proMap.put("productName", document.get("proName"));
			proMap.put("price", document.get("proPrice"));
			proMap.put("productPic", document.get("pic"));
			//proMap.put("isChange", document.get("isChange"));
			//proMap.put("referPrice", document.get("referPrice"));
			proMap.put("payNum", document.get("payNum"));
			//proMap.put("referPrice", document.get("referPrice"));
			list.add(proMap);
			typeMap.put(document.get("typeId"), document.get("typeName"));
		}
		List<Map<String, String>> typeList = new ArrayList<Map<String, String>>();
		//商品类型的遍历
		for (Map.Entry<String, String> entry : typeMap.entrySet()) {
			Map<String, String> typeMap0 = new HashMap<String, String>();
			typeMap0.put("name", entry.getValue());
			typeMap0.put("id", entry.getKey());
			typeList.add(typeMap0);
		}

		Map resultMap = new HashMap();
		resultMap.put("productMessage", list);
		resultMap.put("productType", typeList);

		indexReader.close();
		return resultMap;
	}

	public static Map<String, String> searchOneDocument(String proId) throws IOException, ParseException {
		Path path = FileSystems.getDefault().getPath(IndexPath.PATH);
		Directory directory = FSDirectory.open(path);
		// ���������鿴��
		IndexReader indexReader = DirectoryReader.open(directory);
		// ����������
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// ��������
		String[] queries = { proId };
		String[] fields = { "id" };
		// ָ����ƥ���ĵ�����γ����Ӿ�
		BooleanClause.Occur[] clauses = { BooleanClause.Occur.MUST };
		Query query = MultiFieldQueryParser.parse(queries, fields, clauses, new IKAnalyzer());
		TopDocs topDocs = indexSearcher.search(query, 1);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;

		ScoreDoc scoreDoc = scoreDocs[0];
		Document document = indexSearcher.doc(scoreDoc.doc);

		Map<String, String> map = new HashMap<String, String>();

		map.put("proId", document.get("id"));
		map.put("proName", document.get("proName"));
		map.put("url", document.get("pic"));
		map.put("proPrice", document.get("proPrice"));
		map.put("ifCanChange", document.get("isChange"));
		map.put("referPrice", document.get("referPrice"));
		map.put("payNum", document.get("payNum"));
		map.put("proType", document.get("typeId"));
		map.put("typeName", document.get("typeName"));
		map.put("brandEn", document.get("brandEn"));
		map.put("brandCn", document.get("brandCn"));
		map.put("proIntroduce", document.get("proIntroduce"));
		map.put("marketId", document.get("marketId"));
		map.put("priginalPrice", document.get("riginalPrice"));
		indexReader.close();
		return map;

	}

}
