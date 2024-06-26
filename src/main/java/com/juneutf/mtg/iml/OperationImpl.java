package com.juneutf.mtg.iml;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juneutf.mtg.mapper.OperationMapper;
import com.juneutf.mtg.model.OperationModel;
import com.juneutf.mtg.model.TableModel;
import com.juneutf.mtg.service.OperationService;

@Service
public class OperationImpl implements OperationService{
    @Autowired
    private OperationMapper mapper;
    
    /**
     * 担当者に関連する操作モデルのリストを取得します。
     *
     * @return 担当者リスト
     */
    @Override
    public ArrayList<OperationModel> selectCharge() {
        return mapper.selectCharge();
    }

    /**
     * 内容に関連する操作モデルのリストを取得します。
     *
     * @return 内容リスト
     */
    @Override
    public ArrayList<OperationModel> selectPurpose() {
        return mapper.selectPurpose();
    }

    /**
     * 指定されたテーブルモデルのIDと名前IDに基づいてデータを削除します。
     *
     * @param TableModel テーブルモデル
     * @return 削除された行数
     */
    @Override
    public int deleteByIdAndtableName(TableModel model) {
        return mapper.deleteByIdAndtableName(model);
    }

    /**
     * 指定されたテーブルモデルのIDと名前IDに基づいてデータを復元します。
     *
     * @param TableModel テーブルモデル
     * @return 復元された行数
     */
    @Override
    public int restoreByIdAndtableName(TableModel model) {
        return mapper.restoreByIdAndtableName(model);
    }

    /**
     * 指定されたテーブルモデルのIDと名前IDに基づいてデータを編集します。
     *
     * @param TableModel テーブルモデル
     * @return 編集された行数
     */
    @Override
    public int editByIdAndtableName(TableModel model) {
        return mapper.editByIdAndtableName(model);
    }

    /**
     * 指定されたテーブルモデルのIDと名前IDに基づいてデータを登録します。
     *
     * @param TableModel テーブルモデル
     * @return 登録された行数
     */
    @Override
    public int regByIdAndtableName(TableModel model) {
        return mapper.regByIdAndtableName(model);
    }
    /**
     * 内容新規登録の業務検証
     * @param TableModel 登録対象のデータを指定する TableModel オブジェクト
     * @return 存在場合はTrue、存在ない場合はFalse.
     */
	@Override
	public List<TableModel> checkUserNameAndEmail(TableModel model) {
		return mapper.checkUserNameAndEmail(model);
	}
	/**
     * 内容新規登録の業務検証
     * @param TableModel 登録対象のデータを指定する TableModel オブジェクト
     * @return 存在場合はTrue、存在ない場合はFalse.
     */
	@Override
	public List<TableModel> checkPurpose(TableModel model) {
		return mapper.checkPurpose(model);
	}
	
	@Override
	public TableModel selectPurposeHistory(int id) {
		return mapper.selectPurposeHistory(id);
	}
	@Override
	public TableModel selectChargeHistory(int id) {
		return mapper.selectChargeHistory(id);
	}
	@Override
	public int updatePassword(TableModel model) {
		return mapper.updatePassword(model);
	}
}
