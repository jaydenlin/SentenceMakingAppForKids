package com.asus.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.asus.asyctask.AsyncTaskResponse;
import com.asus.data.ConceptNetData;
import com.asus.data.DBHelper;
import com.asus.data.OntologyData;
import com.asus.data.WikiData;
import com.asus.dialogue.Question;
import com.asus.exception.AskStringNotFound;
import com.asus.exception.AsyncTaskResponseNotFound;
import com.asus.exception.MatchedWordsNotFound;
import com.asus.exception.PhotoIdsNotFound;
import com.asus.exception.TeachStringResponseNotFound;

public abstract class JudgeEngine {

	protected abstract void setKeywords() throws MatchedWordsNotFound;

	public abstract String onRightResponse();
	
	public abstract String onLeaveResponse();
	
	public abstract String onDirtyResponse();
	
	public abstract String onAskResponse();
	
	public abstract String onShitResponse();

	public abstract String onWrongResponse();

	public abstract String onTeachResponse() throws TeachStringResponseNotFound;

	public abstract String onConfusedResponse();

	public abstract String getNextQuestion();

	public abstract String getCurrentQuestion();

	public abstract int getTeachPhoto() throws PhotoIdsNotFound;

	public abstract int[] getHintPhotos() throws PhotoIdsNotFound;

	public String teachString = "";

	protected OntologyData ontologyData;
	protected ConceptNetData conceptNetData;
	protected WikiData wikiData;
	protected Question question;
	protected String answer;
	protected String[] keywords;
	protected List<String> demoSentences = new ArrayList<String>();
	protected JudgeEngineCallback judgeEngineCallback;
	private boolean isRight;
	private boolean isConfused;
	private boolean isAnswering;
	private boolean isAsking;
	private boolean isLeaving;
	private static String rightAnswer = "";
	private static String wrongAnswer = "";
	private static String rawAnswer = "";
	private String nounString="蘋果|香蕉|床|牛肉|鳥|小鳥|書本|鍋子|麵包|蛋糕|甘蔗|貓|小貓|貓咪|文蛤|哈密瓜|椅子|起司|櫻桃|雞肉|筷子|衣服|雞|公雞|椰子|可樂|棉被|電腦|餅乾|玉米|牛|乳牛|蟹|螃蟹|奶油|鹿|小鹿|梅花鹿|碗盤|狗|小狗|狗狗|狗兒|海豚|鴨|鴨子|榴槤|雞蛋|魚|魚兒|魚肉|叉子|長頸鹿|鵝|天鵝|葡萄|葡萄柚|芭樂|火腿|河馬|馬|馬兒|馬匹|冰淇淋|泡麵|果汁|棗子|奇異果|檸檬|獅子|荔枝|龍眼|枇杷|芒果|牛奶|猴子|香瓜|羊肉|麵|麵條|章魚|橘子|蚵仔|貓熊|褲子|木瓜|紙|紙張|桃|桃子|梨子|筆|原子筆|鋼筆|柿子|手機|洋芋片|豬|小豬|胖豬|枕頭|鳳梨|李子|柚子|豬肉|馬鈴薯|布丁|兔|兔子|鼠|老鼠|白飯|沙拉醬|洗髮精|鯊魚|羊|綿羊|蝦|蝦子|蛇|大蛇|小蛇|毒蛇|肥皂|汽水|沙發|鍋鏟|花枝|楊桃|饅頭|草莓|釋迦|桌子|老虎|土司|豆腐|馬桶|番茄|牙刷|牙膏|烏龜|電視|內衣|內褲|蔬菜|西瓜|蓮霧|鯨魚|地瓜|優格|優酪乳|斑馬";
	private String adjString="紅冬冬的|紅咚咚的|紅通通的|甜甜的|甜的|圓圓的|圓形的|紅色的|火紅色的|新鮮的|黃澄澄的|甜甜的|甜的|長長的|彎彎的|軟軟的|黃色的|黃黃的|沉甸甸的|方方正正的|溫暖的|整齊的|白花花的|紅冬冬的|紅咚咚的|紅通通的|血淋淋的|腥腥的|鹹鹹的|厚厚的|軟軟的|紅色的|紅紅的|毛茸茸的|綠色的|靈敏的|圓鼓鼓的|蓬鬆鬆的|活蹦蹦的|青色的|靈敏的|活潑的|毛茸茸的|綠色的|靈敏的|圓鼓鼓的|蓬鬆鬆的|活蹦蹦的|青色的|靈敏的|活潑的|黑溜溜的|黑漆漆的|黑鴉鴉的|黑壓壓的|黑黝黝的|沉甸甸的|厚厚的|黑色的|鐵錚錚的|黑溜溜的|黑漆漆的|黑沉沉的|黑鴉鴉的|黑壓壓的|黑黝黝的|沉甸甸的|油亮亮的|油漬漬的|烏溜溜的|烏亮亮的|圓圓的|厚厚的|凹凹的|黑色的|黑漆漆的|暖呼呼的|熱呼呼的|熱烘烘的|硬邦邦的|硬梆梆的|硬繃繃的|乾巴巴的|乾癟癟的|香香的|香噴噴的|圓圓的|橢圓形的|硬硬的|黃色的|金黃色的|新鮮的|甜蜜蜜的|軟綿綿的|甜甜的|美味的|三角形的|軟軟的|巧克力色的|咖啡色的|硬繃繃的|硬梆梆的|硬邦邦的|一條條的|甜甜的|長長的|直直的|一根根的|粗粗的|紅色的|紅紅的|毛茸茸的|溫馴的|靈敏的|有花紋的|毛絨絨的|小小的|聰明的|柔和的|溫馴的|善良的|毛茸茸的|溫馴的|靈敏的|有花紋的|毛絨絨的|小小的|聰明的|柔和的|溫馴的|善良的|毛茸茸的|溫馴的|靈敏的|有花紋的|毛絨絨的|小小的|聰明的|柔和的|溫馴的|善良的|硬繃繃的|硬梆梆的|硬邦邦的|滑溜溜的|扇形的|硬硬的|甜蜜蜜的|香香的|甜甜的|圓圓的|圓形的|粗粗的|綠色的|白淨淨的|白皙皙的|硬邦邦的|硬梆梆的|硬繃繃的|硬硬的|白色的|白白的|乳白色的|黃澄澄的|濃濃的|濃郁的|香香的|鹹鹹的|美味的|三角形的|厚厚的|軟軟的|黃色的|紅撲撲的|紅冬冬的|紅咚咚的|紅通通的|甜甜的|圓圓的|圓形的|軟軟的|紅色的|紅紅的|白嫩嫩的|白淨淨的|白皙皙的|白色的|白白的|直挺挺的|長長的|黃色的|蓬鬆鬆的|柔柔的|白色的|蓬鬆鬆的|毛茸茸的|毛絨絨的|圓鼓鼓的|土黃色的|活蹦蹦的|紅色的|火紅色|蓬鬆鬆的|毛茸茸的|毛絨絨的|圓鼓鼓的|土黃色的|活蹦蹦的|紅色的|火紅色|毛茸茸的|毛絨絨的|沉甸甸的|甜甜的|圓形的|圓圓的|粗粗的|土黃色的|咖啡色的|甜甜的|冰涼涼的|涼冰冰的|黏答答的|冰涼的|紅色的|白淨淨的|白皙皙的|蓬鬆鬆的|軟趴趴的|軟綿綿的|薄薄的|軟軟的|白色的|黑漆漆的|黑鴉鴉的|黑壓壓的|黑黝黝的|扁扁的|薄薄的|黑色的|貴重的|脆脆的|黃澄澄的|香香的|香脆的|鹹鹹的|薄薄的|黃色的|黃澄澄的|香噴噴的|熱騰騰的|熱呼呼的|熱烘烘的|香香的|香噴噴的|甜甜的|長長的|軟軟的|黃色的|金黃色的|新鮮的|慢吞吞的|溫馴的|和善的|黃褐色的|慢吞吞的|溫馴的|和善的|黃褐色的|光禿禿的|硬邦邦的|硬梆梆的|硬繃繃的|硬硬的|青色的|綠色的|光禿禿的|硬邦邦的|硬梆梆的|硬繃繃的|硬硬的|青色的|綠色的|白皙皙的|白淨淨的|白皚皚的|香香的|濃濃的|香噴噴的|濃郁的|白色的|白白的|乳白色的|新鮮的|活蹦蹦的|活潑的|活蹦亂跳的|有花紋的|土黃色的|怯生生的|活跳跳的|黃色的|靈敏的|靈活的|溫馴的|活蹦蹦的|活潑的|活蹦亂跳的|有花紋的|土黃色的|怯生生的|活跳跳的|黃色的|靈敏的|靈活的|溫馴的|活蹦蹦的|活潑的|活蹦亂跳的|有花紋的|土黃色的|怯生生的|活跳跳的|黃色的|靈敏的|靈活的|溫馴的|白淨淨的|冰涼涼的|亮晶晶的|光溜溜的|滑溜溜的|圓圓的|滑滑的|冰冷的|白色的|白白的|薄薄的|乾淨的|活蹦蹦的|毛茸茸的|溫馴的|和善的|活潑的|忠心的|白淨淨的|毛絨絨的|活跳跳的|傻楞楞的|白色的|活蹦蹦的|毛茸茸的|溫馴的|和善的|活潑的|忠心的|白淨淨的|毛絨絨的|活跳跳的|傻楞楞的|白色的|活蹦蹦的|毛茸茸的|溫馴的|和善的|活潑的|忠心的|白淨淨的|毛絨絨的|活跳跳的|傻楞楞的|白色的|活蹦蹦的|毛茸茸的|溫馴的|和善的|活潑的|忠心的|白淨淨的|毛絨絨的|活跳跳的|傻楞楞的|白色的|長長的|迅速的|靈敏的|活潑的|灰色的|溫馴的|和善的|聰明的|毛茸茸的|活蹦蹦的|白皙皙的|白色的|白淨淨|毛茸茸的|活蹦蹦的|白皙皙的|白色的|白淨淨|臭臭的|刺鼻的|尖尖的|刺刺的|硬硬的|綠色的|綠綠的|光溜溜的|圓圓的|橢圓形的|輕輕的|黃色的|新鮮的|腥腥的|滑溜溜的|滑滑的|活跳跳的|活蹦蹦的|黑色的|黑黑的|新鮮的|腥腥的|滑溜溜的|滑滑的|活跳跳的|活蹦蹦的|黑色的|黑黑的|新鮮的|白花花的|紅冬冬的|紅咚咚的|紅通通的|血淋淋的|腥腥的|鹹鹹的|厚厚的|紅色的|紅紅的|新鮮的|冰涼涼的|鐵錚錚的|冷冰冰的|涼冰冰的|亮光光的|亮晶晶的|長長的|尖尖的|冰冷的|銀色的|閃亮的|高高的|有花紋的|直愣愣的|直挺挺的|長長的|黃色的|土黃色的|溫馴的|毛茸茸的|白皙皙的|白色的|優雅的|毛絨絨的|溫馴的|毛茸茸的|白皙皙的|白色的|優雅的|毛絨絨的|溫馴的|軟軟的|甜甜的|圓圓的|球狀的|藍色的|新鮮的|黃澄澄的|圓鼓鼓的|酸酸的|圓形的|圓圓的|粗粗的|軟軟的|黃色的|綠油油的|甜甜的|圓圓的|粗粗的|凹凹凸凸的|青色的|綠色的|新鮮的|紅冬冬的|紅咚咚的|紅通通的|香香的|香噴噴的|圓圓的|軟軟的|紅色的|紅紅的|胖嘟嘟的|溼答答的|溼淋淋的|溼漉漉的|粉紅色的|皺巴巴的|慢吞吞的|圓圓的|棕色的|迅速的|跑得快的|活蹦蹦的|棕色的|靈敏的|棕色的|迅速的|跑得快的|活蹦蹦的|棕色的|靈敏的|棕色的|迅速的|跑得快的|活蹦蹦的|棕色的|靈敏的|冰涼涼的|黏答答的|冷冰冰的|涼冰冰的|紅撲撲的|紅冬冬的|紅咚咚的|紅通通的|圓鼓鼓的|香香的|濃濃的|甜甜的|美味的|圓形的|圓圓的|球形的|球狀的|黏黏的|冰冷的|紅色的|紅紅的|粉紅色的|脆脆的|方方正正的|黃色的|冰涼涼的|冷冰冰的|涼冰冰的|甜甜的|冰冷的|橙色的|黃色的|油亮亮的|甜甜的|香香的|圓形的|圓圓的|青色的|綠色的|毛茸茸的|毛絨絨的|香香的|酸酸的|甜甜的|圓形的|圓圓的|軟軟的|綠色的|新鮮的|酸溜溜的|香香的|酸酸的|圓形的|圓圓的|球形的|球狀的|綠色的|綠綠的|毛茸茸的|毛絨絨的|土黃色的|蓬鬆鬆的|兇猛的|紅冬冬的|紅咚咚的|紅通通的|香香的|甜甜的|圓圓的|球形的|球狀的|刺刺的|紅色的|紅紅的|乾巴巴的|甜甜的|圓形的|圓圓的|球形的|球狀的|粗粗的|黃色的|土黃色的|黃澄澄|黏答答的|黃澄澄的|光溜溜的|滑溜溜的|香香的|甜甜的|橢圓形的|滑滑的|紅色的|紅紅的|火紅色的|新鮮的|白淨淨的|冰涼涼的|涼冰冰的|白皙皙的|香香的|濃濃的|涼涼的|白色的|白白的|乳白色的|乳白色的|新鮮的|活蹦蹦的|毛茸茸的|靈敏的|活潑的|聰明的|土黃色的|調皮的|圓鼓鼓的|香香的|甜甜的|圓形的|圓圓的|球形的|球狀的|綠色的|綠綠的|新鮮的|白花花的|紅冬冬的|紅咚咚的|紅通通的|騷騷的|紅色的|紅紅的|白皙皙的|滑溜溜的|油亮亮的|鬆垮垮的|冰涼涼的|涼冰冰的|滑滑的|粗粗的|軟軟的|白色的|白白的|乳白色的|長長的|白皙皙的|滑溜溜的|油亮亮的|鬆垮垮的|冰涼涼的|涼冰冰的|滑滑的|粗粗的|軟軟的|白色的|白白的|乳白色的|長長的|滑溜溜的|軟趴趴的|軟綿綿的|滑滑的|黏黏的|軟軟的|黃澄澄的|香香的|甜甜的|圓形的|圓圓的|球形的|球狀的|黃色的|黃黃的|金黃色的|軟趴趴的|滑溜溜的|腥腥的|鹹鹹的|滑滑的|軟軟的|白色的|毛茸茸的|黑白色的|活潑的|胖嘟嘟的|軟趴趴的|胖胖的|毛絨絨的|溫馴的|皺巴巴的|鬆垮垮的|皺皺的|灰色的|黃澄澄的|香香的|甜甜的|橢圓形的|軟軟的|黃色的|黃黃的|新鮮的|白淨淨的|沉甸甸的|軟軟的|白色的|白白的|白淨淨的|沉甸甸的|軟軟的|白色的|白白的|圓鼓鼓的|白嫩嫩的|甜蜜蜜的|軟綿綿的|甜甜的|圓形的|圓圓的|軟軟的|紅色的|紅紅的|圓鼓鼓的|白嫩嫩的|甜蜜蜜的|軟綿綿的|甜甜的|圓形的|圓圓的|軟軟的|紅色的|紅紅的|胖嘟嘟的|圓鼓鼓的|黃澄澄的|香香的|甜甜的|黃色的|黃黃的|直挺挺的|長長的|銀色的|直挺挺的|長長的|銀色的|直挺挺的|長長的|銀色的|軟綿綿的|軟軟的|香香的|甜甜的|圓形的|圓圓的|紅色的|紅紅的|黑壓壓的|黑溜溜的|黑漆漆的|黑鴉鴉的|黑壓壓的|烏溜溜的|烏亮亮的|長長的|扁扁的|黑色的|黑黑的|全新的|油漬漬的|油亮亮的|油膩膩的|香香的|鹹鹹的|薄薄的|脆脆的|金黃色的|黃色的|胖嘟嘟的|溫馴的|粉紅色的|和善的|胖嘟嘟的|溫馴的|粉紅色的|和善的|胖嘟嘟的|溫馴的|粉紅色的|和善的|白淨淨的|白皙皙的|蓬鬆鬆的|軟綿綿的|長長的|軟軟的|白色的|白白的|乾淨的|黃澄澄的|香香的|甜甜的|酸酸的|圓圓的|橢圓形的|刺刺的|黃色的|黃黃的|金黃色的|圓鼓鼓的|圓形的|圓圓的|滑滑的|紫色的|紫紫的|圓鼓鼓的|香香的|酸酸的|甜甜的|橢圓形的|粗粗的|軟軟的|黃色的|黃黃的|金黃色的|白花花的|紅冬冬的|紅咚咚的|紅通通的|血淋淋的|腥腥的|鹹鹹的|厚厚的|軟軟的|紅色的|紅紅的|乾癟癟的|乾巴巴的|光禿禿的|圓形的|圓圓的|土黃色的|軟綿綿的|黃澄澄的|甜甜的|圓圓的|滑滑的|軟軟的|黃色的|黃黃的|毛茸茸的|灰灰的|灰色的|溫馴的|和善的|跑得快的|怯生生的|毛絨絨的|活跳跳的|活蹦蹦|毛茸茸的|灰灰的|灰色的|溫馴的|和善的|跑得快的|怯生生的|毛絨絨的|活跳跳的|活蹦蹦|毛茸茸的|土黃色的|活潑的|靈敏的|怯生生的|胖嘟嘟的|毛絨絨的|黃色的|毛茸茸的|土黃色的|活潑的|靈敏的|怯生生的|胖嘟嘟的|毛絨絨的|黃色的|熱烘烘的|熱呼呼的|熱騰騰的|圓鼓鼓的|黏黏的|軟軟的|熱熱的|白色的|白白的|油亮亮的|油膩膩的|香香的|甜甜的|黃色的|香噴噴的|滑溜溜的|香香的|香噴噴的|滑滑的|長長的|迅速的|靈敏的|黑白色的|活蹦蹦的|毛茸茸的|白色的|溫馴的|毛絨絨的|活跳跳的|白色的|白白的|活蹦蹦的|毛茸茸的|白色的|溫馴的|毛絨絨的|活跳跳的|白色的|白白的|活蹦蹦的|活跳跳的|綠色的|新鮮的|彎彎的|活蹦蹦的|活跳跳的|綠色的|新鮮的|彎彎的|滑溜溜的|油亮亮的|長長的|滑滑的|有花紋的|滑溜溜的|油亮亮的|長長的|滑滑的|有花紋的|滑溜溜的|油亮亮的|長長的|滑滑的|有花紋的|滑溜溜的|油亮亮的|長長的|滑滑的|有花紋的|白皙皙的|滑溜溜的|香香的|橢圓形的|滑滑的|白色的|白白的|乳白色的|冰涼涼的|涼冰冰的|甜甜的|黏黏的|緊繃繃的|硬繃繃的|硬梆梆的|硬邦邦的|長長的|軟軟的|綠色的|綠綠的|硬邦邦的|硬梆梆的|烏亮亮的|油亮亮的|黑壓壓的|黑鴉鴉的|黑漆漆的|黑溜溜的|長長的|黑色的|黑黑的|滑溜溜的|軟趴趴的|軟綿綿的|黏黏的|軟軟的|白色的|白白的|涼冰冰的|酸溜溜的|冰涼涼的|酸酸的|甜甜的|厚厚的|軟軟的|黃色的|黃黃的|金黃色的|軟綿綿的|熱烘烘的|熱呼呼的|熱騰騰的|暖呼呼的|圓鼓鼓的|圓圓的|軟軟的|米白色的|白色的|紅冬冬的|紅咚咚的|紅通通的|香香的|甜甜的|三角形的|軟軟的|紅色的|紅紅的|新鮮的|軟綿綿的|圓鼓鼓的|甜甜的|圓圓的|圓形的|軟軟的|硬梆梆的|硬繃繃的|硬邦邦的|方方正正的|長長的|咖啡色的|有花紋的|黃黑色的|兇猛的|毛茸茸的|毛絨絨的|靈敏的|軟綿綿的|白嫩嫩的|香香的|長長的|軟軟的|白色的|乳白色|白嫩嫩的|白淨淨的|白皙皙的|軟趴趴的|軟綿綿的|方方正正的|軟軟的|白色的|白白的|乳白色的|白淨淨的|冰涼涼的|冷冰冰的|涼冰冰的|白色的|紅撲撲的|紅冬冬的|紅咚咚的|紅通通的|甜甜的|圓形的|圓圓的|軟軟的|紅色的|紅紅的|長長的|軟軟的|藍色的|藍藍的|香香的|長長的|軟軟的|白色的|白白的|乳白色的|慢吞吞的|橢圓形的|灰色的|緩慢的|慢慢的|圓鼓鼓的|灰色的|黑溜溜的|黑漆漆的|黑沉沉的|黑鴉鴉的|黑壓壓的|黑黝黝的|烏亮亮的|方方正正的|薄薄的|長長的|硬硬的|黑色的|黑黑的|白淨淨的|柔軟的|白色的|白白的|乾淨的|白淨淨的|柔軟的|白色的|白白的|乾淨的|綠油油的|溼答答的|溼淋淋的|溼漉漉的|油亮亮的|青色的|綠色的|綠綠的|綠油油的|圓鼓鼓的|沉甸甸的|甜甜的|圓形的|圓圓的|球形的|球狀的|綠色的|綠綠的|甜甜的|圓圓的|紅色的|紅紅的|新鮮的|巨大的|龐大的|慢吞吞的|溫馴的|和善的|藍色的|水藍色|乾癟癟的|乾巴巴的|紅冬冬的|紅咚咚的|紅通通的|長長的|紅色的|紅紅的|粗粗的|酸溜溜的|白皙皙的|香香的|酸酸的|甜甜的|白色的|白白的|乳白色的|酸溜溜的|白皙皙的|香香的|酸酸的|甜甜的|白色的|白白的|乳白色的|黑白色的|有花紋的|怯生生的|跑得快的|迅速的|活蹦蹦的|靈敏的";
	public JudgeEngine(Question question, String answer,
			JudgeEngineCallback judgeEngineCallback) {
		this.question = question;
		this.ontologyData = OntologyData.getInstance();
		this.answer = answer;
		this.rawAnswer=answer;
		this.wikiData = WikiData.getInstance();
		this.conceptNetData = ConceptNetData.getInstance();
		this.judgeEngineCallback = judgeEngineCallback;

	}

	public void start() {
		
		if(IsAskingThenSetAnswers()){
			judgeEngineCallback.onAsk();
		}else if(IsConfusedThenSetAnswers()){
			judgeEngineCallback.onConfused();
		}else if(IsLeaving()){
			judgeEngineCallback.onLeave();
		}else if(IsAnswering()){
			checkRightOrWrongThenSetAnswersAndDemoString();
		}else{
			judgeEngineCallback.onShit();
		}
	}

	private void checkRightOrWrongThenSetAnswersAndDemoString() {
		try {
			setKeywords();
		} catch (MatchedWordsNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// checkout db
		isRight = false;
		if (keywords != null) {
			for (int i = 0; i < keywords.length; i++) {
				if (answer.indexOf(keywords[i]) != -1) {
					isRight = true;
					rightAnswer = keywords[i];
					break;
				} else {
					// do nothing
					wrongAnswer = answer.replace(question.questionPhrase,"").trim();
				}
			}
		}
		// checkout concept net
		conceptNetData.searchConceptNet(question.questionPhrase, answer,
				new AsyncTaskResponse<List<String>>() {
					@Override
					public void processFinish(List<String> output) {
						if (output.size() > 0) {
							rightAnswer = "";
							demoSentences = output;

							if (isRight != true) {
								isRight = true;
								//answer = "";
							}

						} else {
							wrongAnswer = answer.replace(question.questionPhrase,"").trim();
						}
						
						//check negative terms
						if(answer.matches(".*(的相反|才不是|錯了|哪是|不是|沒有).*")){
							isRight=false;
						}	
						
						// finally
						if (isRight) {
							judgeEngineCallback.onRight();
						} else {
							judgeEngineCallback.onWrong();
						}
						
					}
				});
		
		

	}
	
	private boolean IsAskingThenSetAnswers(){
		String answerInternalToPreventReset=answer;
		isAsking=false;
		if(answer.matches(".*[要知道|想知道|不知道|不會|不確定|不瞭解|不了解|不懂|怎麼|如何|要怎樣|教我].*("+nounString+"|"+adjString+")*[怎麼|如何|怎樣].*造句.*")){//ask for noun
			isAsking=true;
			try {
				question.questionPhrase=getAskString(answerInternalToPreventReset, nounString, 1);
				question.isAskingAdj=true;
				resetAnswers();
			} catch (AskStringNotFound nounAskStringNotFound) {
				
				question.questionPhrase="";
				nounAskStringNotFound.printStackTrace();
				try {
					question.questionPhrase=getAskString(answerInternalToPreventReset, adjString, 1);
					question.isAskingAdj=false;
					resetAnswers();
				} catch (AskStringNotFound adjAskStringNotFound) {
					question.questionPhrase="";
					adjAskStringNotFound.printStackTrace();
				}
				
			}
		}else{
			isAsking=false;
		}
		
		return isAsking;
	}
	
	private boolean IsConfusedThenSetAnswers() {

		isConfused = false;
		if(answer.matches(".*(提示|不知道|不懂|聽不懂|不會|難|換一題|下一|什麼|啥麼|甚麼).*")){
			resetAnswers();
			isConfused=true;
		}
		
		return isConfused;
	}
	
	private boolean IsLeaving(){
		isLeaving = false;
		if(answer.matches(".*(我想要|等一下要|我要|不玩了|無聊|我*點|該去|準備要).*(吃飯|睡覺|睡|出門|出去玩|看電影|去唱歌|去看電影|看電視|玩電腦|弄電腦|上廁所|找朋友).*")){
			resetAnswers();
			isLeaving = true;
		}else if(answer.matches(".*(無聊|好爛|很爛|爛透|不玩了|不想玩).*")){
			resetAnswers();
			isLeaving = true;
		}
		return isLeaving;
	}
	
	
	private boolean IsAnswering(){
		isAnswering=false;
		if(answer.matches(".*"+question.questionPhrase+".*")){
			isAnswering=true;
		}else{
			resetAnswers();
		}
		return isAnswering;
	}
	
	
	public String getRightAnswer() {
		try {
			return rightAnswer;
		} catch (Exception e) {
			Log.w(getClass().getSimpleName(), "rightAnswer is empty");
			return "";
		}
	}

	public String getWrongAnswer() {
		try {
			return wrongAnswer;
		} catch (Exception e) {
			Log.w(getClass().getSimpleName(), "wrongAnswer is empty");
			return "";
		}
	}

	public List<String> getDemoSentences() {
		return demoSentences;
	}

	public void searchWikiData(String searchData,
			AsyncTaskResponse<String> delegate) {

		wikiData.searchWikiData(searchData, delegate);

	}

	public void searchConceptNet(AsyncTaskResponse<List<String>> delegate) {

		conceptNetData.searchConceptNet(question.questionPhrase, answer,
				delegate);
	}
	
	public String getRawAnswer() {
		return rawAnswer;
	}
	
	private String getAskString(String data, String findString, int groupIndex) throws AskStringNotFound{
		Pattern pattern=Pattern.compile(".*("+findString+").*");
		Matcher matcher=pattern.matcher(data);
		if(matcher.matches()){
			return matcher.group(groupIndex);
		}else{
			throw new AskStringNotFound("AskString Not Found");
		}
	}
	
	private void resetAnswers(){
		//answer = "";
		rightAnswer = "";
		wrongAnswer = "";
	}

}
