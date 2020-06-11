package chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.BoardUtils;
import chess.engine.board.Move;
import chess.engine.board.Move.PawnMove;
import chess.engine.board.Move.PawnJump;
import chess.engine.board.Move.PawnAttackMove;
import chess.engine.board.Move.PawnPromotion;
import chess.gui.PromotionWindow;
import chess.gui.Table;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece {

	private final static int[] CANDIDATE_MOVE_COORDINATES = { 8, 16, 7, 9 };
	
	public Pawn(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.PAWN, piecePosition, pieceAlliance, true);
	}

	public Pawn(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
		super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			
			final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);
			
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
					if((Table.humanAlliance.isWhite() && this.getPieceAlliance().isWhite()) || (Table.humanAlliance.isBlack() && this.getPieceAlliance().isBlack())){
						legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate), PromotionWindow.promoteTo, true));
					}else {
						legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate), "queen", false));
						legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate), "rook", false));
						legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate), "knight", false));
						legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate), "bishop", false));
					}
				}else {
					legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
				}
			} else if(currentCandidateOffset == 16 && this.isFirstMove() && ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) || (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))) {
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
				}
			} else if(currentCandidateOffset == 7 && !((BoardUtils.EIGHTH_COLUMN[piecePosition] && this.pieceAlliance.isWhite()) || (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
							if((Table.humanAlliance.isWhite() && this.getPieceAlliance().isWhite()) || (Table.humanAlliance.isBlack() && this.getPieceAlliance().isBlack())){
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PromotionWindow.promoteTo, true));
							}else {
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), "queen", false));
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), "rook", false));
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), "knight", false));
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), "bishop", false));
							}
						}else {
							legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}else if(board.getEnPassantPawn() != null) {
					if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))){
						final Piece pieceOnCandidate = board.getEnPassantPawn();
						if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
							legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
			} else if(currentCandidateOffset == 9 && !((BoardUtils.FIRST_COLUMN[piecePosition] && this.pieceAlliance.isWhite()) || (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)){
							if((Table.humanAlliance.isWhite() && this.getPieceAlliance().isWhite()) || (Table.humanAlliance.isBlack() && this.getPieceAlliance().isBlack())){
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), PromotionWindow.promoteTo, true));
							}else {
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), "queen", false));
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), "rook", false));
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), "knight", false));
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate), "bishop", false));
							}
						}else {
							legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}else if(board.getEnPassantPawn() != null) {
					if(board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))){
						final Piece pieceOnCandidate = board.getEnPassantPawn();
						if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
							legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				}
			}
			
		}
		
		return ImmutableList.copyOf(legalMoves);
	}

	@Override
	public Piece movePiece(final Move move) {
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

	@Override
	public String toString() {
		return Piece.PieceType.PAWN.toString();
	}

	public Piece getPromotionPiece(String promoteTo) {
		if(promoteTo == "rook"){
			return new Rook(this.pieceAlliance, this.piecePosition, false);
		}else if(promoteTo == "bishop"){
			return new Bishop(this.pieceAlliance, this.piecePosition, false);
		}else if(promoteTo == "knight") {
			return new Knight(this.pieceAlliance, this.piecePosition, false);
		}else {
			return new Queen(this.pieceAlliance, this.piecePosition, false);
		}
	}
	
}
