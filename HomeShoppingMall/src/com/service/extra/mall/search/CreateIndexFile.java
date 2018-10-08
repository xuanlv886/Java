package com.service.extra.mall.search;

import static org.apache.lucene.document.TextField.TYPE_STORED;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleDocValuesField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.service.extra.mall.model.vo.ProductVo;

import util.IndexPath;






public class CreateIndexFile    {
	
	
	
	public void ForIndexCreate(List<ProductVo> list) throws Exception{
		
		Path path = FileSystems.getDefault().getPath(IndexPath.PATH);
			//创建Directory对象
			Directory directory =null;
			IndexWriter indexWriter=null;
			//指定索引库的存放位置Directory对象
			directory = FSDirectory.open(path);
			if(indexWriter == null){
				//使用IK分词器,对文档内容进行分析
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new IKAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE);
				indexWriter = new IndexWriter(directory, indexWriterConfig);
			}
			CreateIndexFile creatIndexFile = new CreateIndexFile();
			//格式化十进制数字
			DecimalFormat df = new DecimalFormat("#0.00");
			for(int i=0;i<list.size();i++){
				Map<String,Object> parameterMap = new HashMap<String,Object>();
				//设置分词关键字
				String searchWord =(list.get(i).getpName()+list.get(i).getTypeName()+list.get(i).getPPname()+list.get(i).getpDescribe()).replace(",", "");
				//String marketId = list.get(i).getMarketId();
				String proId = list.get(i).getpId();
				String proName = list.get(i).getpName();
				//String url = list.get(i).getUrl();
				double proPrice = list.get(i).getpNowPrice();
				//int ifCanChange = list.get(i).getIfCanChange();
				//double referPrice = list.get(i).getReferPrice();
				//支付方式
				int payNum = 0;
				int stockNum = list.get(i).getpStockNum();
				int totalNum = list.get(i).getpTotalNum();
				payNum = totalNum - stockNum;
				double originalPrice = list.get(i).getpOriginalPrice();
				String proType = list.get(i).getPtdId();
				String typeName  = list.get(i).getTypeName();
				//String brandEn = list.get(i).getBrandEN();
				//String brandCn = list.get(i).getBrandCN();
				String proDescribe = list.get(i).getpDescribe();
				String property = list.get(i).getPPname();
				//String cproperty = list.get(i).getPRCPname();
				parameterMap.put("searchWord", searchWord);
				//parameterMap.put("marketId", marketId);
				parameterMap.put("proId", proId);
				parameterMap.put("proName", proName);
				//parameterMap.put("url", url);
				parameterMap.put("proPrice",df.format(proPrice) );
				parameterMap.put("originalPrice",df.format(originalPrice) );
				//parameterMap.put("ifCanChange", ifCanChange);
				parameterMap.put("payNum", payNum);
				parameterMap.put("proType", proType);
				parameterMap.put("typeName", typeName);
				//parameterMap.put("referPrice", df.format(referPrice));
				//parameterMap.put("brandEn", brandEn);
				//parameterMap.put("brandCn", brandCn);
				parameterMap.put("proDescribe", proDescribe);
				parameterMap.put("property", property);
				//parameterMap.put("cproperty", cproperty);
				creatIndexFile.creatIndexFile(parameterMap,indexWriter);
			}
			indexWriter.commit();
			indexWriter.close();
		}


	public void creatIndexFile(Map<String,Object> map ,IndexWriter indexWriter) throws IOException {
		// TODO Auto-generated method stub
		Document document = new Document();
        String searchWord = ""+map.get("proName")+map.get("typeName")+map.get("proDescribe")+map.get("property");
        document.add(new Field("searchWord",searchWord,TYPE_STORED));
        //document.add(new StoredField("marketId",""+map.get("marketId"),TYPE_STORED));
        document.add(new StoredField("id",""+map.get("proId"),TYPE_STORED ));
		document.add(new Field("proName",""+ map.get("proName"), TYPE_STORED));
		document.add(new Field("pic",""+map.get("url"),TYPE_STORED));
		document.add(new DoubleDocValuesField("proPrice", Double.valueOf(""+map.get("proPrice"))));
		document.add(new Field("proPrice",""+map.get("proPrice"),TYPE_STORED));
		document.add(new Field("originalPrice",""+map.get("originalPrice"),TYPE_STORED));
		//document.add(new Field("isChange",""+map.get("ifCanChange"),TYPE_STORED));
		//document.add(new Field("referPrice",""+map.get("referPrice"),TYPE_STORED));
		document.add(new NumericDocValuesField("payNum", Long.valueOf(""+map.get("payNum"))));
		document.add(new Field("payNum",""+map.get("payNum"),TYPE_STORED));
		document.add(new StoredField("typeId",""+map.get("proType"),TYPE_STORED));
		document.add(new Field("typeName",""+map.get("typeName"),TYPE_STORED ));
		//document.add(new Field("brandEn",""+map.get("brandEn"),TYPE_STORED ));
		//document.add(new Field("brandCn",""+map.get("brandCn"),TYPE_STORED ));
		document.add(new Field("proDescribe",""+map.get("proDescribe"),TYPE_STORED ));
		document.add(new Field("property",""+map.get("property"),TYPE_STORED));
		//document.add(new Field("cproperty",""+map.get("cproperty"),TYPE_STORED));
		indexWriter.addDocument(document);
		
	}


	



	







	

	
	        
}
