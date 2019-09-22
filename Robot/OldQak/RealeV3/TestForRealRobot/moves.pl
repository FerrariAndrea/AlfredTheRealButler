%%
%% moves.pl
%%

%% move(M)  M=w|..

mapdims(0,0).
wduration(0).
direction(downDir).

showMoves :- move( M ), output( move( M ) ), fail.
showMoves.			

output( M ) :- stdout <- println( M ).

%-------------------------------------------------
%  TuProlog FEATURES 
%-------------------------------------------------
dialog( FileName ) :-  
	java_object('javax.swing.JFileChooser', [], Dialog),
	Dialog <- showOpenDialog(_),
	Dialog <- getSelectedFile returns File,
	File <- getName returns FileName. 		 


initMoveKb :- output("moves kb loaded").
:- initialization(initMoveKb).


