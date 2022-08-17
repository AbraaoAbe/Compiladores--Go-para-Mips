package code;

/*
 * Enumeração das instruções aceitas pela TM (Tiny Machine).
 * Adaptado do arquivo 'instruction.h'. 
 */
public enum OpCode {
	// ---------------------------------------------------
	// Basic ops
	
    HALT("HALT", 0),
    NOOP("NOOP", 0),

    // ---------------------------------------------------
    // Arith ops MIPS
    ADD("add", 3),	// add $1, $2, $3	; $1 <- $2 + $3
    ADDi("addi", 3),	// addi $1, $2, int	; $1 <- $2 + int
    SUB("sub", 3),	// sub $1, $2, $3	; $1 <- $2 - $3
    SUBf("sub.d", 3),	// SUBf fx, fy, fz	; fx <- fy - fz
    MUL("mul", 3),	// mul $1,$2,$3     ; $1 <- $2 * $3
    MULt("mult", 2),	// mult $2,$3	; $hi,$low <- $2*$3; upper 32 bits stored in hi; lower 32 bits stored in low
    DIV("div", 3),	// div $2,$3	; $hi,$low <- $2/$3; remainder stored in hi: quocient stored in low;
    //DIVf("DIVf", 3),	// DIVf fx, fy, fz	; fx <- fy / fz

    //ADDi("ADDi", 3),	// ADDi ix, iy, iz	; ix <- iy + iz
    // ADDf("ADDf", 3),	// ADDf fx, fy, fz	; fx <- fy + fz
    // SUBi("SUBi", 3),	// SUBi ix, iy, iz	; ix <- iy - iz
    // SUBf("SUBf", 3),	// SUBf fx, fy, fz	; fx <- fy - fz
    // MULi("MULi", 3),	// MULi ix, iy, iz	; ix <- iy * iz
    // MULf("MULf", 3),	// MULf fx, fy, fz	; fx <- fy * fz
    // DIVi("DIVi", 3),	// DIVi ix, iy, iz	; ix <- iy / iz
    // DIVf("DIVf", 3),	// DIVf fx, fy, fz	; fx <- fy / fz
    // Widen to float
    WIDf("WIDf", 2),	// WIDf fx, iy		; fx <- (float) iy

    // ---------------------------------------------------
    // Logic ops MIPS
    AND("and", 3),       // and $1,$2,$3     ; $1 <- $2 & $3
    ANDi("and", 3),      // andi $1,$2,100   ; $1 <- $2 & 100
    OR("or", 3),         // or $1,$2,$3      ; $1 <- $2 | $3
    ORi("ori", 3),       // ori $1,$2,100    ; $1 <- $2 | 100

    // Comparsion
    SLT("slt", 3),    //slt $1,$2,$3 ; if($2 < $3) $1 <- 1; else $1 <- 0
    SLTi("slti", 3),  //slti $1,$2,100 ; if($2 < 100) $1 <- 1; else $1 <- 0

    // Logical OR
    OROR("OROR", 3), 	// OROR ix, iy, iz	; ix <- (bool) iy || (bool) iz
    // Equality
    EQUi("EQUi", 3), 	// EQUi ix, iy, iz	; ix <- iy == iz ? 1 : 0
    EQUf("EQUf", 3),	// EQUf ix, fy, fz	; ix <- fy == fz ? 1 : 0
    EQUs("EQUs", 3), 	// EQUs ix, iy, iz	; ix <- str_tab[iy] == str_tab[iz] ? 1 : 0
    // Less than
    LTHi("LTHi", 3), 	// LTHi ix, iy, iz	; ix <- iy < iz ? 1 : 0
    LTHf("LTHf", 3), 	// LTHi ix, fy, fz	; ix <- iy < iz ? 1 : 0
    LTHs("LTHs", 3), 	// LTHs ix, iy, iz	; ix <- str_tab[iy] < str_tab[iz] ? 1 : 0

    // ---------------------------------------------------
    // Branches and jumps

    // Jumps
    JMP("j", 1),	    // j 1000		; go to address 1000
    JMPr("jr", 1),	// jr $1        ; go to address stored in $1
    JMPal("jal", 1), // jal 1000     ; $ra=PC+4; go to address 1000
    
    // Branch on equal/not equal
    BEQ("beq", 2), 	// beq $1,$2,Label    ; if($1 == $2) go to Label
    BNE("bne", 2), 	// bne $1,$2,Label    ; if($1 != $2) go to Label 
    // Branch on greater/less
    BGT("bgt", 2), 	// bgt $1,$2,Label    ; if($1 > $2) go to Label
    BLT("blt", 2), 	// blt $1,$2,Label    ; if($1 < $2) go to Label
    // Branch on greater/less than or equal
    BGE("bge", 2), 	// bge $1,$2,Label    ; if($1 >= $2) go to Label
    BLE("ble", 2), 	// ble $1,$2,Label    ; if($1 <= $2) go to Label
    
    // Absolute jump
    JUMP("JUMP", 1),	// JUMP addr		; PC <- addr
    // Branch on true
    BOTb("BOTb", 2), 	// BOTb ix, off		; PC <- PC + off, if ix == 1
    // Branch on false
    BOFb("BOFb", 2),	// BOFb ix, off		; PC <- PC + off, if ix == 0

    // ---------------------------------------------------
    // Loads, stores and moves for MIPS
    LABEL("label", 1),
    
    // Load word (from address or label)
    LDW("lw", 2), 	// lw $1, 100($2)	; $1 <- Memory[$2+100]
    LA("la", 2),     //la $1, label  ; $1 <- Address of label
    
    //Load immediate (constant)
    LI("li", 2), 	//li $1,100     ; $1 <- 100

    // Store word (to address)
    SW("sw", 2),  	// sw $1,100($2); Memory[$2+100] <- $1

    // Moves
    MV("move", 2),  	// move $1,$2;   $1 <- $2
    MVFH("mfhi", 1), // mfhi $2;      $2 <- hi 
    MVFL("mflo", 1), // mflo $2;      $2 <- lo


    // Load word (from address)
    LDWi("LDWi", 2), 	// LDWi ix, addr	; ix <- data_mem[addr]
    LDWf("LDWf", 2), 	// LDWf fx, addr	; fx <- data_mem[addr]
    // Load immediate (constant)
    LDIi("li", 2), 	// LDIi ix, int_const	; ix <- int_const
    LDIf("LDIf", 2),  	// LDIf fx, float_const	; fx <- float_const (must be inside an int)
    // Store word (to address)
    STWi("STWi", 2),  	// STWi addr, ix		; data_mem[addr] <- ix
    STWf("STWf", 2),  	// STWf addr, fx		; data_mem[addr] <- fx
           

    // ---------------------------------------------------
    // Strings
    
    // (These strings instructions are obviously not present in real archs.
    //  The rationale for creating and using them here is simply for convenience:
    //  Normally, these are handled by a low level language lib or OS system call
    //  such as those present in 'libc', etc. However, involving real OS interfaces
    //  here will complicate the game considerably. Thus, we cheat by creating
    //  this high level interface for string handling.)
    
    // Store string
    SSTR("SSTR", 1),  // SSTR str_const		; str_tab <- str_const
    // Catenate
    CATs("CATs", 3),  // CATs ix, iy, iz	; str_tab[ix] <- str_tab[iy] + str_tab[iz]
    // Bool to String
    B2Ss("B2Ss", 2),  // B2Ss ix, iy		; ix <- @str_tab <- register iy (as str)
    // Integer to String
    I2Ss("I2Ss", 2),  // I2Ss ix, iy		; ix <- @str_tab <- register iy (as str)
    // Real to String
    R2Ss("R2Ss", 2),  // R2Ss ix, fy		; ix <- @str_tab <- register fy (as str)

    // ---------------------------------------------------
    // System calls, for I/O (see below)
    SYCL("syscall", 1), //syscall

    CALL("CALL", 2); // CALL code, x
	
	// CALL (very basic simulation of OS system calls)
	// . code: in &v0 sets the operation to be called.
	// . x: register involved in the operation.
	// List of calls:
	// ----------------------------------------------------------------------------
	// code | x  | Description
    // in $v0
	// ----------- -----------------------------------------------------------
	// 1   | $a0  | Print integer number (32 bit): $a0 = integer to be printed
	// 2   | $f12 | Print floating-point number (32 bit): $f12 = float to be printed
	// 3   | $f12 | Print floating-point number (64 bit): $f12 = float to be printed
	// 4   | $a0  | Print string: $a0 = address of string in memory to be printed
	// 5   | $v0  | Read integer:  Integer returned in $v0
	// 6   | $f0  | Read floating-point (32 bit): Float returned in $f0
	// 7   | $f0  | Read floating-point (64 bit): Float returned in $f0
	// 8   | $a0  | Read str: $a0 = memory address of string input buffer 
    //                    $a1 = length of string buffer (n)
    // 10  | $v0  | exit program
	// ----------------------------------------------------------------------------
	// OBS.: All strings in memory are null ('\0') terminated, like in C.
	// ----------------------------------------------------------------------------
	
	public final String name;
	public final int opCount;
	
	private OpCode(String name, int opCount) {
		this.name = name;
		this.opCount = opCount;
	}
	
	public String toString() {
		return this.name;
	}
	
}


