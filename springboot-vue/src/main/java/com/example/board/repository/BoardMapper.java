package com.example.board.repository;

import java.util.List;

import com.example.board.domain.BoardVO;
import com.example.board.domain.ReplyVO;

public interface BoardMapper {
  

  public List<BoardVO> selectBoardList(BoardVO vo) throws Exception;
  

  public int selectBoardListCount(BoardVO vo) throws Exception;


  public BoardVO selectBoardDetail(BoardVO vo) throws Exception;


  public int insertBoard(BoardVO vo) throws Exception;


  public int updateBoard(BoardVO vo) throws Exception;


  public int deleteBoard(BoardVO vo) throws Exception;


  public void increaseViewCount(BoardVO vo) throws Exception;


  public List<ReplyVO> selectReplyList(ReplyVO vo) throws Exception;


  public int insertReply(ReplyVO vo) throws Exception;


  public int updateReply(ReplyVO vo) throws Exception;


  public int deleteReply(ReplyVO vo) throws Exception;


}
