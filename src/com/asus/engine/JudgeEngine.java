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
	private String nounString="ī�G|����|��|����|��|�p��|�ѥ�|��l|�ѥ]|�J�||�̽�|��|�p��|�߫}|���|���K��|�Ȥl|�_�q|���|����|�_�l|��A|��|����|���l|�i��|�ֳQ|�q��|�氮|�ɦ�|��|�Ť�|��|����|���o|��|�p��|�����|�J�L|��|�p��|����|����|���b|�n|�n�l|�h��|���J|��|����|����|�e�l|���V��|�Z|���Z|����|����c|�ݼ�|���L|�e��|��|����|����|�B�N�O|�w��|�G��|�Ǥl|�_���G|�f�c|��l|��K|�s��|�J�I|�~�G|����|�U�l|����|�Ϧ�|��|�ѱ�|����|��l|�H�J|�ߺ�|�Ǥl|���|��|�ȱi|��|��l|���l|��|��l��|����|�U�l|���|�v����|��|�p��|�D��|�E�Y|���|���l|�c�l|�ަ�|���a��|���B|��|�ߤl|��|�ѹ�|�ն�|�F����|�~�v��|�T��|��|����|��|���l|�D|�j�D|�p�D|�r�D|�Ψm|�T��|�F�o|����|��K|����|�C�Y|���|���{|��l|�Ѫ�|�g�q|���G|����|�f�X|����|���I|�Q�t|�q��|����|����|����|���|����|�H��|�a��|�u��|�u�T��|����";
	private String adjString="���V�V��|���N�N��|���q�q��|������|����|��ꪺ|��Ϊ�|���⪺|�����⪺|�s�A��|����᪺|������|����|������|�s�s��|�n�n��|���⪺|������|�I�l�l��|��西����|�ŷx��|�����|�ժ�᪺|���V�V��|���N�N��|���q�q��|��O�O��|�{�{��|���Ъ�|�p�p��|�n�n��|���⪺|������|����|��⪺|�F�Ӫ�|�깪����|���P�P��|�����۪�|�C�⪺|�F�Ӫ�|���⪺|����|��⪺|�F�Ӫ�|�깪����|���P�P��|�����۪�|�C�⪺|�F�Ӫ�|���⪺|�·ȷȪ�|�º�����|�¾~�~��|��������|���K�K��|�I�l�l��|�p�p��|�¦⪺|�K������|�·ȷȪ�|�º�����|�¨I�I��|�¾~�~��|��������|���K�K��|�I�l�l��|�o�G�G��|�o�{�{��|�Q�ȷȪ�|�Q�G�G��|��ꪺ|�p�p��|�W�W��|�¦⪺|�º�����|�x�I�I��|���I�I��|���M�M��|�w������|�w������|�w�^�^��|���ڤڪ�|��çç��|������|���Q�Q��|��ꪺ|���Ϊ�|�w�w��|���⪺|�����⪺|�s�A��|���e�e��|�n������|������|������|�T���Ϊ�|�n�n��|���J�O�⪺|�@�ئ⪺|�w�^�^��|�w������|�w������|�@������|������|������|������|�@�ڮڪ�|�ʲʪ�|���⪺|������|����|�Ź���|�F�Ӫ�|���᯾��|�򵳵���|�p�p��|�o����|�X�M��|�Ź���|���}��|����|�Ź���|�F�Ӫ�|���᯾��|�򵳵���|�p�p��|�o����|�X�M��|�Ź���|���}��|����|�Ź���|�F�Ӫ�|���᯾��|�򵳵���|�p�p��|�o����|�X�M��|�Ź���|���}��|�w�^�^��|�w������|�w������|�ƷȷȪ�|���Ϊ�|�w�w��|���e�e��|������|������|��ꪺ|��Ϊ�|�ʲʪ�|��⪺|�ղb�b��|�����ժ�|�w������|�w������|�w�^�^��|�w�w��|�զ⪺|�եժ�|�ťզ⪺|����᪺|�@�@��|�@����|������|���Ъ�|������|�T���Ϊ�|�p�p��|�n�n��|���⪺|��������|���V�V��|���N�N��|���q�q��|������|��ꪺ|��Ϊ�|�n�n��|���⪺|������|�չ�઺|�ղb�b��|�����ժ�|�զ⪺|�եժ�|��������|������|���⪺|���P�P��|�X�X��|�զ⪺|���P�P��|����|�򵳵���|�깪����|�g���⪺|�����۪�|���⪺|������|���P�P��|����|�򵳵���|�깪����|�g���⪺|�����۪�|���⪺|������|����|�򵳵���|�I�l�l��|������|��Ϊ�|��ꪺ|�ʲʪ�|�g���⪺|�@�ئ⪺|������|�B�D�D��|�D�B�B��|�H������|�B�D��|���⪺|�ղb�b��|�����ժ�|���P�P��|�n�w�w��|�n������|������|�n�n��|�զ⪺|�º�����|�¾~�~��|��������|���K�K��|���|������|�¦⪺|�Q����|�ܯܪ�|����᪺|������|���ܪ�|���Ъ�|������|���⪺|����᪺|���Q�Q��|�����˪�|���I�I��|���M�M��|������|���Q�Q��|������|������|�n�n��|���⪺|�����⪺|�s�A��|�C�]�]��|�Ź���|�M����|���Ŧ⪺|�C�]�]��|�Ź���|�M����|���Ŧ⪺|���r�r��|�w������|�w������|�w�^�^��|�w�w��|�C�⪺|��⪺|���r�r��|�w������|�w������|�w�^�^��|�w�w��|�C�⪺|��⪺|�����ժ�|�ղb�b��|�սJ�J��|������|�@�@��|���Q�Q��|�@����|�զ⪺|�եժ�|�ťզ⪺|�s�A��|�����۪�|���⪺|���۶ø���|���᯾��|�g���⪺|�ĥͥͪ�|��������|���⪺|�F�Ӫ�|�F����|�Ź���|�����۪�|���⪺|���۶ø���|���᯾��|�g���⪺|�ĥͥͪ�|��������|���⪺|�F�Ӫ�|�F����|�Ź���|�����۪�|���⪺|���۶ø���|���᯾��|�g���⪺|�ĥͥͪ�|��������|���⪺|�F�Ӫ�|�F����|�Ź���|�ղb�b��|�B�D�D��|�G������|���ȷȪ�|�ƷȷȪ�|��ꪺ|�Ʒƪ�|�B�N��|�զ⪺|�եժ�|������|���b��|�����۪�|����|�Ź���|�M����|���⪺|���ߪ�|�ղb�b��|�򵳵���|��������|�̷�����|�զ⪺|�����۪�|����|�Ź���|�M����|���⪺|���ߪ�|�ղb�b��|�򵳵���|��������|�̷�����|�զ⪺|�����۪�|����|�Ź���|�M����|���⪺|���ߪ�|�ղb�b��|�򵳵���|��������|�̷�����|�զ⪺|�����۪�|����|�Ź���|�M����|���⪺|���ߪ�|�ղb�b��|�򵳵���|��������|�̷�����|�զ⪺|������|���t��|�F�Ӫ�|���⪺|�Ǧ⪺|�Ź���|�M����|�o����|����|�����۪�|�����ժ�|�զ⪺|�ղb�b|����|�����۪�|�����ժ�|�զ⪺|�ղb�b|��䪺|���|�y�y��|��몺|�w�w��|��⪺|���|���ȷȪ�|��ꪺ|���Ϊ�|������|���⪺|�s�A��|�{�{��|�ƷȷȪ�|�Ʒƪ�|��������|�����۪�|�¦⪺|�¶ª�|�s�A��|�{�{��|�ƷȷȪ�|�Ʒƪ�|��������|�����۪�|�¦⪺|�¶ª�|�s�A��|�ժ�᪺|���V�V��|���N�N��|���q�q��|��O�O��|�{�{��|���Ъ�|�p�p��|���⪺|������|�s�A��|�B�D�D��|�K������|�N�B�B��|�D�B�B��|�G������|�G������|������|�y�y��|�B�N��|�Ȧ⪺|�{�G��|������|���᯾��|���h�h��|��������|������|���⪺|�g���⪺|�Ź���|����|�����ժ�|�զ⪺|�u����|�򵳵���|�Ź���|����|�����ժ�|�զ⪺|�u����|�򵳵���|�Ź���|�n�n��|������|��ꪺ|�y����|�Ŧ⪺|�s�A��|����᪺|�깪����|�ĻĪ�|��Ϊ�|��ꪺ|�ʲʪ�|�n�n��|���⪺|��o�o��|������|��ꪺ|�ʲʪ�|�W�W�Y�Y��|�C�⪺|��⪺|�s�A��|���V�V��|���N�N��|���q�q��|������|���Q�Q��|��ꪺ|�n�n��|���⪺|������|�D�ʹʪ�|�õ�����|�òO�O��|���u�u��|�����⪺|�K�ڤڪ�|�C�]�]��|��ꪺ|�Ħ⪺|���t��|�]�o�֪�|�����۪�|�Ħ⪺|�F�Ӫ�|�Ħ⪺|���t��|�]�o�֪�|�����۪�|�Ħ⪺|�F�Ӫ�|�Ħ⪺|���t��|�]�o�֪�|�����۪�|�Ħ⪺|�F�Ӫ�|�B�D�D��|�H������|�N�B�B��|�D�B�B��|��������|���V�V��|���N�N��|���q�q��|�깪����|������|�@�@��|������|������|��Ϊ�|��ꪺ|�y�Ϊ�|�y����|�H�H��|�B�N��|���⪺|������|�����⪺|�ܯܪ�|��西����|���⪺|�B�D�D��|�N�B�B��|�D�B�B��|������|�B�N��|��⪺|���⪺|�o�G�G��|������|������|��Ϊ�|��ꪺ|�C�⪺|��⪺|����|�򵳵���|������|�ĻĪ�|������|��Ϊ�|��ꪺ|�n�n��|��⪺|�s�A��|�ķȷȪ�|������|�ĻĪ�|��Ϊ�|��ꪺ|�y�Ϊ�|�y����|��⪺|���|����|�򵳵���|�g���⪺|���P�P��|���r��|���V�V��|���N�N��|���q�q��|������|������|��ꪺ|�y�Ϊ�|�y����|��몺|���⪺|������|���ڤڪ�|������|��Ϊ�|��ꪺ|�y�Ϊ�|�y����|�ʲʪ�|���⪺|�g���⪺|�����|�H������|����᪺|���ȷȪ�|�ƷȷȪ�|������|������|���Ϊ�|�Ʒƪ�|���⪺|������|�����⪺|�s�A��|�ղb�b��|�B�D�D��|�D�B�B��|�����ժ�|������|�@�@��|�D�D��|�զ⪺|�եժ�|�ťզ⪺|�ťզ⪺|�s�A��|�����۪�|����|�F�Ӫ�|���⪺|�o����|�g���⪺|�ե֪�|�깪����|������|������|��Ϊ�|��ꪺ|�y�Ϊ�|�y����|��⪺|���|�s�A��|�ժ�᪺|���V�V��|���N�N��|���q�q��|���̪�|���⪺|������|�����ժ�|�ƷȷȪ�|�o�G�G��|�P������|�B�D�D��|�D�B�B��|�Ʒƪ�|�ʲʪ�|�n�n��|�զ⪺|�եժ�|�ťզ⪺|������|�����ժ�|�ƷȷȪ�|�o�G�G��|�P������|�B�D�D��|�D�B�B��|�Ʒƪ�|�ʲʪ�|�n�n��|�զ⪺|�եժ�|�ťզ⪺|������|�ƷȷȪ�|�n�w�w��|�n������|�Ʒƪ�|�H�H��|�n�n��|����᪺|������|������|��Ϊ�|��ꪺ|�y�Ϊ�|�y����|���⪺|������|�����⪺|�n�w�w��|�ƷȷȪ�|�{�{��|���Ъ�|�Ʒƪ�|�n�n��|�զ⪺|����|�¥զ⪺|���⪺|�D�ʹʪ�|�n�w�w��|�D�D��|�򵳵���|�Ź���|�K�ڤڪ�|�P������|�K�K��|�Ǧ⪺|����᪺|������|������|���Ϊ�|�n�n��|���⪺|������|�s�A��|�ղb�b��|�I�l�l��|�n�n��|�զ⪺|�եժ�|�ղb�b��|�I�l�l��|�n�n��|�զ⪺|�եժ�|�깪����|�չ�઺|���e�e��|�n������|������|��Ϊ�|��ꪺ|�n�n��|���⪺|������|�깪����|�չ�઺|���e�e��|�n������|������|��Ϊ�|��ꪺ|�n�n��|���⪺|������|�D�ʹʪ�|�깪����|����᪺|������|������|���⪺|������|��������|������|�Ȧ⪺|��������|������|�Ȧ⪺|��������|������|�Ȧ⪺|�n������|�n�n��|������|������|��Ϊ�|��ꪺ|���⪺|������|��������|�·ȷȪ�|�º�����|�¾~�~��|��������|�Q�ȷȪ�|�Q�G�G��|������|���|�¦⪺|�¶ª�|���s��|�o�{�{��|�o�G�G��|�o������|������|���Ъ�|������|�ܯܪ�|�����⪺|���⪺|�D�ʹʪ�|�Ź���|�����⪺|�M����|�D�ʹʪ�|�Ź���|�����⪺|�M����|�D�ʹʪ�|�Ź���|�����⪺|�M����|�ղb�b��|�����ժ�|���P�P��|�n������|������|�n�n��|�զ⪺|�եժ�|���b��|����᪺|������|������|�ĻĪ�|��ꪺ|���Ϊ�|��몺|���⪺|������|�����⪺|�깪����|��Ϊ�|��ꪺ|�Ʒƪ�|���⪺|������|�깪����|������|�ĻĪ�|������|���Ϊ�|�ʲʪ�|�n�n��|���⪺|������|�����⪺|�ժ�᪺|���V�V��|���N�N��|���q�q��|��O�O��|�{�{��|���Ъ�|�p�p��|�n�n��|���⪺|������|��çç��|���ڤڪ�|���r�r��|��Ϊ�|��ꪺ|�g���⪺|�n������|����᪺|������|��ꪺ|�Ʒƪ�|�n�n��|���⪺|������|����|�ǦǪ�|�Ǧ⪺|�Ź���|�M����|�]�o�֪�|�ĥͥͪ�|�򵳵���|��������|������|����|�ǦǪ�|�Ǧ⪺|�Ź���|�M����|�]�o�֪�|�ĥͥͪ�|�򵳵���|��������|������|����|�g���⪺|���⪺|�F�Ӫ�|�ĥͥͪ�|�D�ʹʪ�|�򵳵���|���⪺|����|�g���⪺|���⪺|�F�Ӫ�|�ĥͥͪ�|�D�ʹʪ�|�򵳵���|���⪺|���M�M��|���I�I��|�����˪�|�깪����|�H�H��|�n�n��|������|�զ⪺|�եժ�|�o�G�G��|�o������|������|������|���⪺|���Q�Q��|�ƷȷȪ�|������|���Q�Q��|�Ʒƪ�|������|���t��|�F�Ӫ�|�¥զ⪺|�����۪�|����|�զ⪺|�Ź���|�򵳵���|��������|�զ⪺|�եժ�|�����۪�|����|�զ⪺|�Ź���|�򵳵���|��������|�զ⪺|�եժ�|�����۪�|��������|��⪺|�s�A��|�s�s��|�����۪�|��������|��⪺|�s�A��|�s�s��|�ƷȷȪ�|�o�G�G��|������|�Ʒƪ�|���᯾��|�ƷȷȪ�|�o�G�G��|������|�Ʒƪ�|���᯾��|�ƷȷȪ�|�o�G�G��|������|�Ʒƪ�|���᯾��|�ƷȷȪ�|�o�G�G��|������|�Ʒƪ�|���᯾��|�����ժ�|�ƷȷȪ�|������|���Ϊ�|�Ʒƪ�|�զ⪺|�եժ�|�ťզ⪺|�B�D�D��|�D�B�B��|������|�H�H��|���^�^��|�w�^�^��|�w������|�w������|������|�n�n��|��⪺|���|�w������|�w������|�Q�G�G��|�o�G�G��|��������|�¾~�~��|�º�����|�·ȷȪ�|������|�¦⪺|�¶ª�|�ƷȷȪ�|�n�w�w��|�n������|�H�H��|�n�n��|�զ⪺|�եժ�|�D�B�B��|�ķȷȪ�|�B�D�D��|�ĻĪ�|������|�p�p��|�n�n��|���⪺|������|�����⪺|�n������|���M�M��|���I�I��|�����˪�|�x�I�I��|�깪����|��ꪺ|�n�n��|�̥զ⪺|�զ⪺|���V�V��|���N�N��|���q�q��|������|������|�T���Ϊ�|�n�n��|���⪺|������|�s�A��|�n������|�깪����|������|��ꪺ|��Ϊ�|�n�n��|�w������|�w�^�^��|�w������|��西����|������|�@�ئ⪺|���᯾��|���¦⪺|���r��|����|�򵳵���|�F�Ӫ�|�n������|�չ�઺|������|������|�n�n��|�զ⪺|�ťզ�|�չ�઺|�ղb�b��|�����ժ�|�n�w�w��|�n������|��西����|�n�n��|�զ⪺|�եժ�|�ťզ⪺|�ղb�b��|�B�D�D��|�N�B�B��|�D�B�B��|�զ⪺|��������|���V�V��|���N�N��|���q�q��|������|��Ϊ�|��ꪺ|�n�n��|���⪺|������|������|�n�n��|�Ŧ⪺|���Ū�|������|������|�n�n��|�զ⪺|�եժ�|�ťզ⪺|�C�]�]��|���Ϊ�|�Ǧ⪺|�w�C��|�C�C��|�깪����|�Ǧ⪺|�·ȷȪ�|�º�����|�¨I�I��|�¾~�~��|��������|���K�K��|�Q�G�G��|��西����|������|������|�w�w��|�¦⪺|�¶ª�|�ղb�b��|�X�n��|�զ⪺|�եժ�|���b��|�ղb�b��|�X�n��|�զ⪺|�եժ�|���b��|��o�o��|�õ�����|�òO�O��|���u�u��|�o�G�G��|�C�⪺|��⪺|���|��o�o��|�깪����|�I�l�l��|������|��Ϊ�|��ꪺ|�y�Ϊ�|�y����|��⪺|���|������|��ꪺ|���⪺|������|�s�A��|���j��|�e�j��|�C�]�]��|�Ź���|�M����|�Ŧ⪺|���Ŧ�|��çç��|���ڤڪ�|���V�V��|���N�N��|���q�q��|������|���⪺|������|�ʲʪ�|�ķȷȪ�|�����ժ�|������|�ĻĪ�|������|�զ⪺|�եժ�|�ťզ⪺|�ķȷȪ�|�����ժ�|������|�ĻĪ�|������|�զ⪺|�եժ�|�ťզ⪺|�¥զ⪺|���᯾��|�ĥͥͪ�|�]�o�֪�|���t��|�����۪�|�F�Ӫ�";
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
						if(answer.matches(".*(���ۤ�|�~���O|���F|���O|���O|�S��).*")){
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
		if(answer.matches(".*[�n���D|�Q���D|�����D|���||���T�w|���A��|���F��|����|���|�p��|�n���|�Ч�].*("+nounString+"|"+adjString+")*[���|�p��|���].*�y�y.*")){//ask for noun
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
		if(answer.matches(".*(����|�����D|����|ť����|���||��|���@�D|�U�@|����|ԣ��|�ƻ�).*")){
			resetAnswers();
			isConfused=true;
		}
		
		return isConfused;
	}
	
	private boolean IsLeaving(){
		isLeaving = false;
		if(answer.matches(".*(�ڷQ�n|���@�U�n|�ڭn|�����F|�L��|��*�I|�ӥh|�ǳƭn).*(�Y��|��ı|��|�X��|�X�h��|�ݹq�v|�h�ۺq|�h�ݹq�v|�ݹq��|���q��|�˹q��|�W�Z��|��B��).*")){
			resetAnswers();
			isLeaving = true;
		}else if(answer.matches(".*(�L��|�n��|����|��z|�����F|���Q��).*")){
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
